package com.robinsonir.fitnesstracker.data.repository.workout;

import java.time.OffsetDateTime;

public record WorkoutDTO(
        Long id,
        Long customerId,
        String workoutType,
        Integer calories,
        Integer durationMinutes,
        OffsetDateTime workoutDate
) {
}
