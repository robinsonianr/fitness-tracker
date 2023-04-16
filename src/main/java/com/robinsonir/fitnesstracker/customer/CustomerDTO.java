package com.robinsonir.fitnesstracker.customer;

import org.springframework.data.relational.core.sql.In;

import java.util.List;

public record CustomerDTO(

        Integer id,
        String name,
        String email,
        Gender gender,
        Integer age,
        List<String> roles,
        String username

) {
}
