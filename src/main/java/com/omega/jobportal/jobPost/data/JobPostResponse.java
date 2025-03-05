package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.company.Organization;
import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;
import com.omega.jobportal.user.data.UserResponse;

import java.time.LocalDateTime;

public record JobPostResponse(
        Long id,
        UserResponse user,
        Organization organization,
        String location,
        String jobTitle,
        String description,
        JobType jobType,
        WorkMode workMode,
        ExperienceLevel experienceLevel,
        boolean isOpen,
        LocalDateTime applicationDeadline,
        LocalDateTime createdAt
) {
}
