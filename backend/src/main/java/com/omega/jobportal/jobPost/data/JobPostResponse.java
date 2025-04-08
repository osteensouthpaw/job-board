package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.company.data.OrganizationResponse;
import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;
import com.omega.jobportal.user.data.UserResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record JobPostResponse(
        Long id,
        UserResponse recruiter,
        OrganizationResponse organization,
        String location,
        String jobTitle,
        String description,
        BigDecimal salary,
        JobType jobType,
        WorkMode workMode,
        ExperienceLevel experienceLevel,
        boolean isOpen,
        int totalApplications,
        LocalDateTime applicationDeadline,
        LocalDateTime createdAt
) {
}
