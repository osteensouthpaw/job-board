package com.omega.jobportal.jobSeekerProfile.data;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.user.data.UserResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record JobSeekerProfileResponse(
        Long id,
        UserResponse jobSeeker,
        BigDecimal currentAnnualSalary,
        String bio,
        String profession,
        String personalWebsiteUrl,
        String linkedInUrl,
        String gitHubUrl,
        String twitterUrl,
        LocalDate dateOfBirth,
        String phone,
        ExperienceLevel experienceLevel,
        List<ExperienceResponse> experiences,
        List<SkillSetResponse> skills,
        List<EducationResponse> educations
) {
}
