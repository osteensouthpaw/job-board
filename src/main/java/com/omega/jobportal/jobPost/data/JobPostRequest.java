package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.WorkMode;

public record JobPostRequest(
        Long recruiterId,
        Long companyId,
        String jobTitle,
        String jobDescription,
        WorkMode workMode,
        JobType jobType,
        ExperienceLevel experienceLevel
        ) {
}
