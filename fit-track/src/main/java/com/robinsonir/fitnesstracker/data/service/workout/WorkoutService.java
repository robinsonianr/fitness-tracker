package com.robinsonir.fitnesstracker.data.service.workout;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import com.robinsonir.fitnesstracker.data.repository.customer.CustomerRepository;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTO;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTOMapper;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutRepository;
import com.robinsonir.fitnesstracker.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService {


    private final WorkoutDTOMapper workoutDTOMapper;

    private final WorkoutRepository workoutRepository;
    private final CustomerRepository customerRepository;

    public WorkoutService(WorkoutDTOMapper workoutDTOMapper,
                          WorkoutRepository workoutRepository,
                          CustomerRepository customerRepository) {
        this.workoutDTOMapper = workoutDTOMapper;
        this.workoutRepository = workoutRepository;
        this.customerRepository = customerRepository;
    }


    public List<WorkoutDTO> getAllWorkouts() {
        return workoutRepository.findAllWorkouts()
                .stream()
                .map(workoutDTOMapper)
                .collect(Collectors.toList());
    }

    public WorkoutDTO getWorkout(Long id) {
        return workoutRepository.findWorkoutById(id)
                .map(workoutDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "workout with id [%s] not found".formatted(id)
                ));
    }

    public void addWorkout(WorkoutCreationRequest workoutCreationRequest) {

        WorkoutEntity newWorkout = new WorkoutEntity();
        newWorkout.setWorkoutType(workoutCreationRequest.workoutType());
        newWorkout.setExercises(workoutCreationRequest.exercises());
        newWorkout.setCalories(workoutCreationRequest.calories());
        newWorkout.setDurationMinutes(workoutCreationRequest.durationMinutes());
        newWorkout.setVolume(workoutCreationRequest.volume());
        newWorkout.setWorkoutDate(workoutCreationRequest.workoutDate());


        Optional<CustomerEntity> customerEntity =  customerRepository.findCustomerById(workoutCreationRequest.customer().getId());
        customerEntity.ifPresent(newWorkout::setCustomer);
        workoutRepository.save(newWorkout);
    }

    public void checkIfCustomerExistsOrThrow(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "workout with id [%s] not found".formatted(id)
            );
        }
    }
}
