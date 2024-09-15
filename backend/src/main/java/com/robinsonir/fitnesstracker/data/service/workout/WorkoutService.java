package com.robinsonir.fitnesstracker.data.service.workout;

import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTO;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTOMapper;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutRepository;
import com.robinsonir.fitnesstracker.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {


    private final WorkoutDTOMapper workoutDTOMapper;

    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutDTOMapper workoutDTOMapper,
                          WorkoutRepository workoutRepository) {
        this.workoutDTOMapper = workoutDTOMapper;
        this.workoutRepository = workoutRepository;
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

        WorkoutEntity newWorkout = new WorkoutEntity(
                workoutCreationRequest.customer(),
                workoutCreationRequest.workoutType(),
                workoutCreationRequest.calories(),
                workoutCreationRequest.durationMinutes(),
                workoutCreationRequest.workoutDate());

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
