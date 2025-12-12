package com.omega.jobportal.jobSeekerProfile.data;

import java.time.LocalDate;

public record CertificationResponse(
        Long id,
        Long jobSeekerId,
        String name,
        String issuer,
        LocalDate issuedOn,
        String credentialId,
        String certificateUrl
) {
}

