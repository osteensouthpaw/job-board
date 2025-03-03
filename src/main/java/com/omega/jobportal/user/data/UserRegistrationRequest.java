package com.omega.jobportal.user.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotBlank(message = "first name is required")
        String firstName,

        @NotBlank(message = "last name is required")
        String lastName,

        @Email
        @NotBlank(message = "email is required")
        String email,

        @Size(min = 8)
        @NotBlank
        String password
) {
}
