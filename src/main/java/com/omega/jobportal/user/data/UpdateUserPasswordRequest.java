package com.omega.jobportal.user.data;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateUserPasswordRequest(
        String oldPassword,

        @NotBlank
        @Length(min = 8)
        String password,

        @NotBlank
        @Length(min = 8)
        String confirmPassword,
        String passwordToken
) {
}
