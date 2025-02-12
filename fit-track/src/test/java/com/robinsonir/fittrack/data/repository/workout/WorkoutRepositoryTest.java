package com.robinsonir.fittrack.data.repository.workout;

import com.robinsonir.fittrack.data.Gender;
import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import com.robinsonir.fittrack.data.entity.workout.WorkoutEntity;
import com.robinsonir.fittrack.data.repository.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
public class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveWorkout() {
        // Create and save a customer
        CustomerEntity customer = new CustomerEntity();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("password123");
        customer.setAge(30);
        customer.setGender(Gender.MALE);

        customerRepository.save(customer);

        // Create a workout entity
        WorkoutEntity workout = new WorkoutEntity();
        workout.setCustomer(customer);
        workout.setWorkoutType("Running");
        workout.setCalories(500);
        workout.setDurationMinutes(60);
        workout.setWorkoutDate(OffsetDateTime.now());

        // Save the workout
        WorkoutEntity savedWorkout = workoutRepository.save(workout);

        // Validate workout is saved and has an ID
        assertThat(savedWorkout.getId()).isNotNull();
        assertThat(savedWorkout.getWorkoutType()).isEqualTo("Running");
        assertThat(savedWorkout.getCalories()).isEqualTo(500);
        assertThat(savedWorkout.getDurationMinutes()).isEqualTo(60);
    }

    @Test
    void testFindWorkoutById() {
        // Create and save a customer
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Jane Doe");
        customer.setEmail("jane.doe@example.com");
        customer.setPassword("securePass");
        customer.setAge(28);
        customer.setGender(Gender.FEMALE);

        customerRepository.save(customer);

        // Create and save a workout
        WorkoutEntity workout = new WorkoutEntity();
        workout.setCustomer(customer);
        workout.setWorkoutType("Cycling");
        workout.setCalories(300);
        workout.setDurationMinutes(45);
        workout.setWorkoutDate(OffsetDateTime.now());
        workoutRepository.save(workout);

        // Fetch the workout by ID
        Optional<WorkoutEntity> foundWorkout = workoutRepository.findWorkoutById(workout.getId());

        // Assert the workout was found and data is correct
        assertTrue(foundWorkout.isPresent());
        assertThat(foundWorkout.get().getWorkoutType()).isEqualTo("Cycling");
    }

    @Test
    void testFindAllWorkouts() {
        // Create and save customers
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setName("Bob Smith");
        customer1.setEmail("bob.smith@example.com");
        customer1.setPassword("bobPass");
        customer1.setAge(35);
        customer1.setGender(Gender.MALE);

        customerRepository.save(customer1);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setName("Alice Johnson");
        customer2.setEmail("alice.johnson@example.com");
        customer2.setPassword("alicePass");
        customer2.setAge(29);
        customer2.setGender(Gender.FEMALE);
        customerRepository.save(customer2);

        // Create and save workouts
        WorkoutEntity workout1 = new WorkoutEntity();
        workout1.setCustomer(customer1);
        workout1.setWorkoutType("Swimming");
        workout1.setCalories(400);
        workout1.setDurationMinutes(50);
        workout1.setWorkoutDate(OffsetDateTime.now());
        workoutRepository.save(workout1);

        WorkoutEntity workout2 = new WorkoutEntity();
        workout2.setCustomer(customer2);
        workout2.setWorkoutType("Yoga");
        workout2.setCalories(200);
        workout2.setDurationMinutes(30);
        workout2.setWorkoutDate(OffsetDateTime.now());
        workoutRepository.save(workout2);

        // Fetch all workouts
        List<WorkoutEntity> workouts = workoutRepository.findAllWorkouts();

        // Assert that the workouts were fetched
        assertThat(workouts).isNotNull();
        assertThat(workouts.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testExistsWorkoutEntityByCustomer() {
        // Create and save a customer
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Chris Evans");
        customer.setEmail("chris.evans@example.com");
        customer.setPassword("captain123");
        customer.setAge(40);
        customer.setGender(Gender.MALE);
        customerRepository.save(customer);

        // Create and save a workout
        WorkoutEntity workout = new WorkoutEntity();
        workout.setCustomer(customer);
        workout.setWorkoutType("Weightlifting");
        workout.setCalories(600);
        workout.setDurationMinutes(75);
        workout.setWorkoutDate(OffsetDateTime.now());
        workoutRepository.save(workout);

        // Test if a workout exists for the customer
        boolean exists = workoutRepository.existsWorkoutEntityByCustomer(customer);
        assertTrue(exists);

        // Test for a non-existent customer (without any workouts)
        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setName("Mark Ruffalo");
        newCustomer.setEmail("mark.ruffalo@example.com");
        newCustomer.setPassword("hulk123");
        newCustomer.setAge(53);
        newCustomer.setGender(Gender.MALE);
        customerRepository.save(newCustomer);

        boolean notExists = workoutRepository.existsWorkoutEntityByCustomer(newCustomer);
        assertFalse(notExists);
    }

    @Test
    void testFindAllWorkoutsByCustomerId() {
        // Create a customer entity
        CustomerEntity customer = new CustomerEntity();
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPassword("password");
        customer.setAge(30);
        customer.setGender(Gender.MALE);

        // Create some workouts for the customer
        WorkoutEntity workout1 = new WorkoutEntity();
        workout1.setWorkoutType("Running");
        workout1.setCalories(500);
        workout1.setDurationMinutes(60);
        workout1.setWorkoutDate(OffsetDateTime.now());
        workout1.setExercises(5);
        workout1.setVolume(1000);
        workout1.setCustomer(customer);

        WorkoutEntity workout2 = new WorkoutEntity();
        workout2.setWorkoutType("Cycling");
        workout2.setCalories(600);
        workout2.setDurationMinutes(45);
        workout2.setWorkoutDate(OffsetDateTime.now());
        workout2.setExercises(4);
        workout2.setVolume(800);
        workout2.setCustomer(customer);

        // Save the customer and workouts
        workoutRepository.save(workout1);
        workoutRepository.save(workout2);

        // When
        List<WorkoutEntity> workouts = workoutRepository.findAllWorkoutsByCustomerId(customer.getId());

        // Then
        assertThat(workouts).isNotEmpty();
        assertThat(workouts.size()).isEqualTo(2);
        assertThat(workouts.get(0).getCustomer().getId()).isEqualTo(customer.getId());
    }
}
