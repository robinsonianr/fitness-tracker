package com.robinsonir.fitnesstracker.data.repository.workout;

public record WorkoutDTO(
        Long id,
        String workoutType,
        Integer calories,
        Integer durationMinutes
) {
}
