package com.omega.jobportal.user.data;

import com.omega.jobportal.user.Gender;
import com.omega.jobportal.user.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotNull(message = "first name is required")
        String firstName,

        @NotNull(message = "last name is required")
        String lastName,

        UserType userType,

        @Email
        @NotNull(message = "email is required")
        String email,

        @Size(min = 8)
        String password,
        Gender gender,
        String imageUrl
) {
}
