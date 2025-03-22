package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;

import java.time.LocalDateTime;

public record JobPostFilterQuery(
        JobType jobType,
        String location,
        WorkMode workMode,
        ExperienceLevel experienceLevel,
        LocalDateTime datePosted
) {
}
