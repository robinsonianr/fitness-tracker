package com.robinsonir.fitnesstracker.data.repository.workout;

import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class WorkoutDTOMapper implements Function<WorkoutEntity, WorkoutDTO> {
    @Override
    public WorkoutDTO apply(WorkoutEntity workoutEntity) {
        return new WorkoutDTO(
                workoutEntity.getId(),
                workoutEntity.getCustomer().getId(),
                workoutEntity.getWorkoutType(),
                workoutEntity.getExercises(),
                workoutEntity.getCalories(),
                workoutEntity.getDurationMinutes(),
                workoutEntity.getVolume(),
                workoutEntity.getWorkoutDate()
        );
    }
}
