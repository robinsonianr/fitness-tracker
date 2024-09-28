package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTO;

import java.time.OffsetDateTime;
import java.util.List;

public record CustomerDTO(
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
        List<WorkoutDTO> workouts,
        String username,
        String profileImageId

) {
}
