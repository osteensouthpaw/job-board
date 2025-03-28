package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record JobPostRequest(
        @Size(min = 3, max = 100, message = "The length must be between 3 - 100")
        @NotBlank
        String jobTitle,

        @Size(min = 10, max = 2000, message = "The length must be between 10 - 2000")
        @NotBlank(message = "jobDescription is required")
        String jobDescription,

        @Positive
        BigDecimal salary,

        @NotBlank
        String location,

        @NotNull(message = "work mode is required")
        WorkMode workMode,

        @NotNull(message = "job type is required")
        JobType jobType,

        @NotNull(message = "experience level is required")
        ExperienceLevel experienceLevel,

        @NotNull
        @Future
        LocalDateTime applicationDeadline
) {
}
