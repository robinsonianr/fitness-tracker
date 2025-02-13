package com.robinsonir.fittrack.security.auth;

public record AuthRequest(
        String email,
        String password
) {
}
