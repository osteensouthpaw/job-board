package com.omega.jobportal.jobSeekerProfile.data;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;


public record JobSeekerProfileRequest(
        @Size(min = 100, max = 1000)
        String bio,
        
        String phone,

        @Size(min = 3, max = 100)
        @NotBlank(message = "profession is required")
        String profession,
        String personalWebsiteUrl,
        String linkedInUrl,
        String gitHubUrl,
        String twitterUrl,

        @Past
        LocalDate dateOfBirth,

        @NotNull(message = "experience level cannot be null")
        ExperienceLevel experienceLevel,

        @PositiveOrZero
        BigDecimal currentAnnualSalary
) {
}
