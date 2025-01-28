package com.omega.jobportal.jobSeekerProfile.data;

import com.omega.jobportal.jobPost.enumerations.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record ExperienceRequest(
        boolean isCurrentJob,

        @NotNull
        JobType jobType,

        @NotBlank
        String jobTitle,

        @NotBlank
        String companyName,
        String jobLocation,

        @NotBlank
        String description,

        @PastOrPresent
        @NotNull
        LocalDate startDate,

        LocalDate endDate
) {
}
