package com.robinsonir.fitnesstracker.data.service.customer;

import com.robinsonir.fitnesstracker.data.Gender;

public record CustomerRegistrationRequest(
        String name,
        String email,
        String password,
        Integer age,
        Gender gender
) {
}
