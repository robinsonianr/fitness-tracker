package com.robinsonir.fittrack.data.repository.workout;

import java.time.OffsetDateTime;

public record Workout(
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
