package com.omega.jobportal.user.data;

import com.omega.jobportal.user.Gender;
import com.omega.jobportal.user.UserType;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        UserType userType,
        String email,
        String password,
        String age,
        Gender gender,
        String imageUrl
) {
}
