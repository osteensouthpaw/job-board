package com.omega.jobportal.jobSeekerProfile.data;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CertificationRequest(
        @NotBlank(message = "Name must be present")
        String name,

        @NotBlank(message = "Issuer cannot be blank")
        String issuer,
        LocalDate issuedOn,
        String credentialId,
        String certificateUrl
) {
}
//name: string;
//issuer: string;
//date?: string; // ISO date
//credentialId?: string;
//certificateUrl?: string;