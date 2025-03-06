package com.omega.jobportal.auth;

public record AuthRequest(
        String email,
        String password
) {
}
