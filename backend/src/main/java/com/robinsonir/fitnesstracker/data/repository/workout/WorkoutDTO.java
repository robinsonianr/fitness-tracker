package com.robinsonir.fitnesstracker.data.repository.workout;

public record WorkoutDTO(
        Long id,
        Long customerId,
        String workoutType,
        Integer calories,
        Integer durationMinutes
) {
}
