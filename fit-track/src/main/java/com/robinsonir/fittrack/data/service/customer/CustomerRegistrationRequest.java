package com.robinsonir.fittrack.data.service.customer;

import com.robinsonir.fittrack.data.Gender;

import java.time.OffsetDateTime;

public record CustomerRegistrationRequest(
        String name,
        String email,
        String password,
        Integer age,
        Gender gender,
        OffsetDateTime memberSince
) {
}
