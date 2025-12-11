package com.omega.jobportal.user.data;

import com.omega.jobportal.user.Gender;
import com.omega.jobportal.user.UserType;
import com.omega.jobportal.user.validator.AllowedUserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotBlank(message = "name is required")
        String name,

        @Email
        @NotBlank(message = "email is required")
        String email,

        @Size(min = 8)
        @NotBlank
        String password,

        @AllowedUserType
        @NotNull(message = "userType is required")
        UserType userType,

        Gender gender,

        String imageUrl
) {
}
