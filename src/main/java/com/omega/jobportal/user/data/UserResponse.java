package com.omega.jobportal.user.data;

import com.omega.jobportal.user.Gender;
import com.omega.jobportal.user.UserType;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String imageUrl,
        Gender gender,
        UserType userType

){}
