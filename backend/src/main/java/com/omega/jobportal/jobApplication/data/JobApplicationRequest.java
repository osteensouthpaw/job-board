package com.omega.jobportal.jobApplication.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record JobApplicationRequest(
        @NotNull(message = "jobPost id is required")
        Long jobPostId,

        @URL(message = "resume url must be a valid url")
        @NotBlank(message = "resume url is required")
        String resumeUrl,
        String coverLetter
) {
}
