package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record JobPostFilterQuery(
        JobType jobType,
        String location,
        WorkMode workMode,
        BigDecimal minSalary,
        BigDecimal maxSalary,
        ExperienceLevel experienceLevel,
        LocalDateTime datePosted
) {
}
