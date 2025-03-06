package com.omega.jobportal.user.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AllowedUserTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedUserType {

    String message() default "Allowed types are RECRUITER or JOB_SEEKER";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
