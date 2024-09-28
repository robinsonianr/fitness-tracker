package com.robinsonir.fitnesstracker.data.service.workout;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;

import java.time.OffsetDateTime;

public record WorkoutCreationRequest(
        CustomerEntity customer,
        Integer exercises,
        String workoutType,
        Integer calories,
        Integer durationMinutes,
        Integer volume,
        OffsetDateTime workoutDate
) {
}
