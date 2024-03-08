package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.entity.workout.WorkoutEntity;

import java.util.List;

public record CustomerDTO(

        Long id,
        String name,
        String email,
        Gender gender,
        Integer age,
        List<String> roles,
        List<WorkoutEntity> workouts,
        String username,
        String profileImageId

) {
}
