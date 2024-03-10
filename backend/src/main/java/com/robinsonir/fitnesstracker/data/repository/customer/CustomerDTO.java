package com.robinsonir.fitnesstracker.data.repository.customer;

import com.robinsonir.fitnesstracker.data.Gender;
import com.robinsonir.fitnesstracker.data.repository.workout.WorkoutDTO;

import java.util.List;

public record CustomerDTO(

        Long id,
        String name,
        String email,
        Gender gender,
        Integer age,
        List<String> roles,
        List<WorkoutDTO> workouts,
        String username,
        String profileImageId

) {
}
