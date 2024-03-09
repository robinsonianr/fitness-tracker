package com.robinsonir.fitnesstracker.data.service.customer;


public record RetrieveCustomerRequest(
        Integer id,
        String firstName,
        String lastName,
        String email,
        int age
) {
}
