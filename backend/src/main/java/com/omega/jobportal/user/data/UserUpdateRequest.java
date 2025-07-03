package com.omega.jobportal.user.data;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record UserUpdateRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @URL
        String imageUrl
) {
}
