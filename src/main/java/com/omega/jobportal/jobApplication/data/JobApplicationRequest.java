package com.omega.jobportal.jobApplication.data;

import jakarta.validation.constraints.NotNull;

public record JobApplicationRequest(
        @NotNull(message = "jobPost id is required")
        Long jobPostId
) {
}
