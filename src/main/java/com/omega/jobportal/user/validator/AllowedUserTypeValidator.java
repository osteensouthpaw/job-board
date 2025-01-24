package com.omega.jobportal.user.validator;

import com.omega.jobportal.user.UserType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

import static com.omega.jobportal.user.UserType.JOB_SEEKER;
import static com.omega.jobportal.user.UserType.RECRUITER;

//the  purpose of this validator is to make sure the user only inputs what they are allowed
// since we have other roles that the application uses as well such as admin

public class AllowedUserTypeValidator implements ConstraintValidator<AllowedUserType, UserType> {
    private final static EnumSet<UserType> allowedUserTypes = EnumSet.of(RECRUITER, JOB_SEEKER);

    @Override
    public boolean isValid(UserType userType, ConstraintValidatorContext context) {
        if (userType == null)
            return true;

        return allowedUserTypes.contains(userType);
    }
}
