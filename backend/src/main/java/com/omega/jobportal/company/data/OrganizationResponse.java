package com.omega.jobportal.company.data;

import java.time.LocalDate;

public record OrganizationResponse(
        Long id,
        String companyName,
        String description,
        Long businessStreamId,
        LocalDate establishmentDate,
        String companyLocation,
        String websiteUrl
) {
}
