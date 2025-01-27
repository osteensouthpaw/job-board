package com.omega.jobportal.jobSeekerProfile.data;

import com.omega.jobportal.jobSeekerProfile.education.EducationLevel;

import java.time.LocalDate;

public record EducationResponse(
        Long id,
        Long jobSeekerId,
        EducationLevel educationLevel,
        String fieldOfStudy,
        String institution,
        LocalDate startDate,
        LocalDate endDate,
        String certificateUrl
) {
}
