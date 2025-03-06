package com.omega.jobportal.jobSeekerProfile.data;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record JobSeekerProfileResponse(
        Long id,
        Long jobSeekerId,
        BigDecimal currentAnnualSalary,
        String bio,
        String profession,
        String personalWebsiteUrl,
        String linkedInUrl,
        String gitHubUrl,
        String twitterUrl,
        LocalDate dateOfBirth,
        ExperienceLevel experienceLevel
) {
}
