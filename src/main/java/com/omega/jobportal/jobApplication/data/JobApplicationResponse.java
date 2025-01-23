package com.omega.jobportal.jobApplication.data;

import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.user.data.UserResponse;

import java.time.LocalDateTime;

public record JobApplicationResponse(
        UserResponse appliedUser,
        JobPostResponse appliedPost,
        LocalDateTime appliedDate
) {
}
