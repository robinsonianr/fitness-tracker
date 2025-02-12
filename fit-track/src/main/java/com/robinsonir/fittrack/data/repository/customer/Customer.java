package com.robinsonir.fittrack.data.repository.customer;

import com.robinsonir.fittrack.data.Gender;
import com.robinsonir.fittrack.data.repository.workout.Workout;

import java.time.OffsetDateTime;
import java.util.List;

public record Customer(
        Long id,
        String name,
        String email,
        Gender gender,
        Integer age,
        Integer weight,
        Integer height,
        Integer weightGoal,
        String activity,
        Integer bodyFat,
        OffsetDateTime memberSince,
        List<String> roles,
        List<Workout> workouts,
        String username,
        String profileImageId
) {
}
