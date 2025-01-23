package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;
import com.omega.jobportal.location.Location;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record JobPostRequest(
        @NotNull(message = "recruiter id is required")
        Long recruiterId,

        @NotNull
        Long companyId,

        @Size(min = 3, max = 100, message = "The length must be between 3 - 100")
        @NotBlank
        String jobTitle,

        @Size(min = 10, max = 1000, message = "The length must be between 3 - 100")
        @NotBlank(message = "jobDescription is required")
        String jobDescription,

        @NotNull
        Location location,

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
