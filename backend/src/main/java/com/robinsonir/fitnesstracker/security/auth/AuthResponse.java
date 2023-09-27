package com.robinsonir.fitnesstracker.security.auth;

import com.robinsonir.fitnesstracker.customer.CustomerDTO;

public record AuthResponse(
        String jwtToken,
        CustomerDTO customerDTO
) {
}
