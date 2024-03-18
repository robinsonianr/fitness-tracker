package com.robinsonir.fitnesstracker.data.service.workout;

import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDAO;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTO;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTOMapper;
import com.robinsonir.fitnesstracker.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    private final WorkoutDAO workoutDAO;
    private final WorkoutDTOMapper workoutDTOMapper;

    public WorkoutService(@Qualifier("jdbc") WorkoutDAO workoutDAO,
                          WorkoutDTOMapper workoutDTOMapper) {
        this.workoutDAO = workoutDAO;
        this.workoutDTOMapper = workoutDTOMapper;
    }


    public List<WorkoutDTO> getAllWorkouts() {
        return workoutDAO.selectAllWorkouts()
                .stream()
                .map(workoutDTOMapper)
                .collect(Collectors.toList());
    }

    public WorkoutDTO getWorkout(Long id) {
        return workoutDAO.selectWorkoutById(id)
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

        workoutDAO.insertWorkout(newWorkout);
    }

    public void deleteWorkoutById(Long id) {
        if (!workoutDAO.existsWorkoutEntityById(id)) {
            throw new ResourceNotFoundException(
                    "workout with id [%s] not found".formatted(id)
            );
        }

        workoutDAO.deleteWorkoutById(id);
    }

    public void checkIfCustomerExistsOrThrow(Long id) {
        if (!workoutDAO.existsWorkoutEntityById(id)) {
            throw new ResourceNotFoundException(
                    "workout with id [%s] not found".formatted(id)
            );
        }
    }
}
