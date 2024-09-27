package com.robinsonir.fitnesstracker.security.auth;

public record AuthRequest(
        String email,
        String password
) {
}
