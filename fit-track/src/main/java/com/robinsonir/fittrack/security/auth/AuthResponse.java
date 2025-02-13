package com.robinsonir.fittrack.security.auth;

import com.robinsonir.fittrack.data.repository.customer.Customer;

public record AuthResponse(
        String jwtToken,
        Customer customer
) {
}
