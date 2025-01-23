package com.omega.jobportal.user.data;

import com.omega.jobportal.user.Gender;
import com.omega.jobportal.user.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotBlank(message = "first name is required")
        String firstName,

        @NotBlank(message = "last name is required")
        String lastName,

        @NotNull(message = "userType is required")
        UserType userType,

        @Email
        @NotBlank(message = "email is required")
        String email,

        String phone,

        @Size(min = 8)
        @NotBlank
        String password,

        Gender gender,


        String imageUrl
) {
}
