package com.omega.jobportal.jobSeekerProfile.data;

import com.omega.jobportal.jobPost.enumerations.JobType;

import java.time.LocalDate;

public record ExperienceResponse(
        boolean isCurrentJob,
        JobType jobType,
        String jobTitle,
        String companyName,
        String jobLocation,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
