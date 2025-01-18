package com.omega.jobportal.jobPost.data;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;
import com.omega.jobportal.location.Location;

public record JobPostRequest(
        Long recruiterId,
        Long companyId,
        String jobTitle,
        String jobDescription,
        Location location,
        WorkMode workMode,
        JobType jobType,
        ExperienceLevel experienceLevel
        ) {
}
