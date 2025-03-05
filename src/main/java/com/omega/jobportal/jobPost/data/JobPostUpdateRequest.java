package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record JobPostUpdateRequest(
        @Size(min = 3, max = 100, message = "The length must be between 3 - 100")
        @NotBlank
        String jobTitle,

        @Size(min = 10, max = 1000, message = "The length must be between 3 - 100")
        @NotBlank(message = "jobDescription is required")
        String jobDescription,

        @Digits(integer = 3, fraction = 2)
        @Positive
        BigDecimal hourlyRate,

        @NotBlank(message = "location is required")
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
