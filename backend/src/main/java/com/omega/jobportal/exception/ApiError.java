package com.omega.jobportal.exception;

import java.time.LocalDateTime;

public record ApiError(
        Object message,
        int httpStatus,
        LocalDateTime timestamp,
        String path
) {
}
