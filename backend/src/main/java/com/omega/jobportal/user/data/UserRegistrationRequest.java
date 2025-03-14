package com.omega.jobportal.user.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotBlank(message = "name is required")
        String name,

        @Email
        @NotBlank(message = "email is required")
        String email,

        @Size(min = 8)
        @NotBlank
        String password
) {
}
