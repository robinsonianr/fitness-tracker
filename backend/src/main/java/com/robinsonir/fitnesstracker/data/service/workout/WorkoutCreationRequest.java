package com.robinsonir.fitnesstracker.data.service.workout;

import com.robinsonir.fitnesstracker.data.entity.customer.CustomerEntity;

public record WorkoutCreationRequest(
        CustomerEntity customer,
        String workoutType,
        Integer calories,
        Integer durationMinutes
) {
}
