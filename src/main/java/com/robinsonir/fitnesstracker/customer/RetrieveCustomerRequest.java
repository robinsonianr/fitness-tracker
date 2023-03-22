package com.robinsonir.fitnesstracker.customer;

import java.util.UUID;

public record RetrieveCustomerRequest(
        UUID id,
        String firstName,
        String lastName,
        String email,
        int age
) {
}
