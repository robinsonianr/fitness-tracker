package com.robinsonir.fitnesstracker.customer;


public record RetrieveCustomerRequest(
        Integer id,
        String firstName,
        String lastName,
        String email,
        int age
) {
}
