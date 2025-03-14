package com.omega.jobportal.user.data;

import com.omega.jobportal.user.Gender;
import com.omega.jobportal.user.UserType;
import com.omega.jobportal.user.validator.AllowedUserType;
import jakarta.validation.constraints.NotNull;

public record CompleteRegistrationRequest(
        @NotNull
        Long userId,

        @AllowedUserType
        @NotNull(message = "userType is required")
        UserType userType,

        Gender gender,

        String imageUrl
) {
}
