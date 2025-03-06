package com.omega.jobportal.jobSeekerProfile.data;

import com.omega.jobportal.jobSeekerProfile.education.EducationLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record EducationRequest(
        @NotNull
        EducationLevel educationLevel,

        @NotBlank
        String fieldOfStudy,

        @NotBlank
        String institution,

        @PastOrPresent
        LocalDate startDate,

        @NotNull
        LocalDate endDate,

        String certificateUrl
) {
}
