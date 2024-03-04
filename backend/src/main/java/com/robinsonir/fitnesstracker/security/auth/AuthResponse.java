package com.robinsonir.fitnesstracker.security.auth;

import com.robinsonir.fitnesstracker.data.repository.customer.CustomerDTO;

public record AuthResponse(
        String jwtToken,
        CustomerDTO customerDTO
) {
}
