package com.robinsonir.fittrack.data.service.workout;

import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import com.robinsonir.fittrack.data.entity.workout.WorkoutEntity;
import com.robinsonir.fittrack.data.repository.customer.CustomerRepository;
import com.robinsonir.fittrack.data.repository.workout.Workout;
import com.robinsonir.fittrack.data.repository.workout.WorkoutRepository;
import com.robinsonir.fittrack.exception.ResourceNotFoundException;
import com.robinsonir.fittrack.mappers.WorkoutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {


    private final WorkoutMapper workoutMapper;

    private final WorkoutRepository workoutRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public WorkoutService(WorkoutMapper workoutMapper,
                          WorkoutRepository workoutRepository,
                          CustomerRepository customerRepository) {
        this.workoutMapper = workoutMapper;
        this.workoutRepository = workoutRepository;
        this.customerRepository = customerRepository;
    }


    public List<Workout> getAllWorkouts() {
        List<WorkoutEntity> workoutEntities = new ArrayList<>(workoutRepository.findAllWorkouts());
        return workoutMapper.convertWorkoutEntityListToWorkoutList(workoutEntities);
    }

    public Workout getWorkout(Long id) {
        WorkoutEntity workoutEntity = workoutRepository.findWorkoutById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "workout with id [%s] not found".formatted(id)
                ));
        return workoutMapper.convertWorkoutEntityToWorkout(workoutEntity);
    }

    public List<Workout> getAllWorkoutsByCustomerId(Long id) {
        List<WorkoutEntity> workoutEntities = new ArrayList<>(workoutRepository.findAllWorkoutsByCustomerId(id));
        return workoutMapper.convertWorkoutEntityListToWorkoutList(workoutEntities);
    }

    public void addWorkout(WorkoutCreationRequest workoutCreationRequest) {

        WorkoutEntity newWorkout = new WorkoutEntity();
        newWorkout.setWorkoutType(workoutCreationRequest.workoutType());
        newWorkout.setExercises(workoutCreationRequest.exercises());
        newWorkout.setCalories(workoutCreationRequest.calories());
        newWorkout.setDurationMinutes(workoutCreationRequest.durationMinutes());
        newWorkout.setVolume(workoutCreationRequest.volume());
        newWorkout.setWorkoutDate(workoutCreationRequest.workoutDate());


        Optional<CustomerEntity> customerEntity = customerRepository.findCustomerById(workoutCreationRequest.customer().getId());
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
