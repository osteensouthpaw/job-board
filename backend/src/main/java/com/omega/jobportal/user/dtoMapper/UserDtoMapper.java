package com.omega.jobportal.user.dtoMapper;

import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.data.UserResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDtoMapper implements Function<AppUser, UserResponse> {
    @Override
    public UserResponse apply(AppUser user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getImageUrl(),
                user.getGender(),
                user.getUserType()
        );
    }
}
