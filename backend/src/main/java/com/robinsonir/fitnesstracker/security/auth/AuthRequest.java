package com.robinsonir.fitnesstracker.security.auth;

public record AuthRequest(
        String username,
        String password
) {
}
