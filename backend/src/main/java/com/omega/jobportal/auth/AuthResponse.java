package com.omega.jobportal.auth;

import com.omega.jobportal.user.data.UserResponse;

public record AuthResponse(
        UserResponse userResponse,
        String token
) {
}
