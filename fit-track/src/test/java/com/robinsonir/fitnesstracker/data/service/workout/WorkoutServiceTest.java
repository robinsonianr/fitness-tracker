package com.robinsonir.fitnesstracker.data.service.workout;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRepository;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTO;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTOMapper;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutRepository;
import com.robinsonir.fitnesstracker.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private WorkoutDTOMapper workoutDTOMapper;

    @InjectMocks
    private WorkoutService workoutService;

    private CustomerEntity customer;

    @BeforeEach
    void setUp() {
        customer = new CustomerEntity(1L, "John Doe", "john.doe@example.com", "password", 30, Gender.MALE);
    }

    @Test
    void getAllWorkouts() {
        // Arrange
        WorkoutEntity workout1 = new WorkoutEntity(customer, "Running", 500, 60, OffsetDateTime.now());
        WorkoutEntity workout2 = new WorkoutEntity(customer, "Cycling", 300, 45, OffsetDateTime.now());

        List<WorkoutEntity> workoutList = List.of(workout1, workout2);
        when(workoutRepository.findAllWorkouts()).thenReturn(workoutList);

        WorkoutDTO workoutDTO1 = new WorkoutDTO(1L, customer.getId(), "Running", 500, 60, OffsetDateTime.now());
        WorkoutDTO workoutDTO2 = new WorkoutDTO(2L, customer.getId(), "Cycling", 300, 45, OffsetDateTime.now());

        when(workoutDTOMapper.apply(workout1)).thenReturn(workoutDTO1);
        when(workoutDTOMapper.apply(workout2)).thenReturn(workoutDTO2);

        // Act
        List<WorkoutDTO> result = workoutService.getAllWorkouts();

        // Assert
        assertEquals(2, result.size());
        assertEquals(List.of(workoutDTO1, workoutDTO2), result);
        verify(workoutRepository, times(1)).findAllWorkouts();
        verify(workoutDTOMapper, times(2)).apply(any(WorkoutEntity.class));
    }

    @Test
    void getWorkoutSuccess() {
        // Arrange
        Long workoutId = 1L;
        WorkoutEntity workout = new WorkoutEntity(customer, "Running", 500, 60, OffsetDateTime.now());
        WorkoutDTO expectedDTO = new WorkoutDTO(workoutId, customer.getId(), "Running", 500, 60, OffsetDateTime.now());

        when(workoutRepository.findWorkoutById(workoutId)).thenReturn(Optional.of(workout));
        when(workoutDTOMapper.apply(workout)).thenReturn(expectedDTO);

        // Act
        WorkoutDTO result = workoutService.getWorkout(workoutId);

        // Assert
        assertEquals(expectedDTO, result);
        verify(workoutRepository, times(1)).findWorkoutById(workoutId);
        verify(workoutDTOMapper, times(1)).apply(workout);
    }

    @Test
    void getWorkoutThrowsResourceNotFoundException() {
        // Arrange
        Long workoutId = 1L;
        when(workoutRepository.findWorkoutById(workoutId)).thenReturn(Optional.empty());

        // Act and Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> workoutService.getWorkout(workoutId));

        assertEquals("workout with id [1] not found", exception.getMessage());
        verify(workoutRepository, times(1)).findWorkoutById(workoutId);
        verify(workoutDTOMapper, times(0)).apply(any(WorkoutEntity.class));
    }

    @Test
    void addWorkout() {
        // Arrange
        WorkoutCreationRequest workoutCreationRequest = new WorkoutCreationRequest(customer, "Swimming", 400, 60, OffsetDateTime.now());

        WorkoutEntity newWorkout = new WorkoutEntity();
        newWorkout.setWorkoutType(workoutCreationRequest.workoutType());
        newWorkout.setCalories(workoutCreationRequest.calories());
        newWorkout.setDurationMinutes(workoutCreationRequest.durationMinutes());
        newWorkout.setWorkoutDate(workoutCreationRequest.workoutDate());

        Optional<CustomerEntity> cust = customerRepository.findCustomerById(customer.getId());
        cust.ifPresent(newWorkout::setCustomer);

        when(workoutRepository.save(any(WorkoutEntity.class))).thenReturn(newWorkout);

        // Act
        workoutService.addWorkout(workoutCreationRequest);

        // Assert
        verify(workoutRepository, times(1)).save(any(WorkoutEntity.class));
    }

    @Test
    void checkIfCustomerExistsOrThrowSuccess() {
        // Arrange
        Long workoutId = 1L;
        when(workoutRepository.existsById(workoutId)).thenReturn(true);

        // Act and Assert
        assertDoesNotThrow(() -> workoutService.checkIfCustomerExistsOrThrow(workoutId));
        verify(workoutRepository, times(1)).existsById(workoutId);
    }

    @Test
    void checkIfCustomerExistsOrThrowThrowsResourceNotFoundException() {
        // Arrange
        Long workoutId = 1L;
        when(workoutRepository.existsById(workoutId)).thenReturn(false);

        // Act and Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> workoutService.checkIfCustomerExistsOrThrow(workoutId));

        assertEquals("workout with id [1] not found", exception.getMessage());
        verify(workoutRepository, times(1)).existsById(workoutId);
    }
}
