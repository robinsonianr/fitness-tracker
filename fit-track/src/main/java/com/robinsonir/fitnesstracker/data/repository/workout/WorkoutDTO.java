package com.robinsonir.fitnesstracker.data.repository.workout;

import java.time.OffsetDateTime;

public record WorkoutDTO(
        Long id,
        Long customerId,
        String workoutType,
        Integer exercises,
        Integer calories,
        Integer durationMinutes,
        Integer volume,
        OffsetDateTime workoutDate
) {
}
