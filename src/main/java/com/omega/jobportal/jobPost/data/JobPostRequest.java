package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.jobPost.JobType;
import com.omega.jobportal.jobPost.WorkMode;

public record JobPostRequest(
        Long recruiterId,
        Long companyId,
        String jobTitle,
        String jobDescription,
        WorkMode workMode,
        JobType jobType
        ) {
}
