package com.omega.jobportal.user.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(
        @Email
        @NotBlank
        String email
) {
}
