package com.robinsonir.fitnesstracker.customer;

public record CustomerRegistrationRequest(
        String username,
        String name,
        String email,
        String password,
        Integer age,
        Gender gender
) {
}
