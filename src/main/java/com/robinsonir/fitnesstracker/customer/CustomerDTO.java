package com.robinsonir.fitnesstracker.customer;

public record CustomerDTO(
        Integer id,
        String name,
        String email,
        String password,
        int age
) {
}
