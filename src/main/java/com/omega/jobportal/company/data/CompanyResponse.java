package com.omega.jobportal.company.data;

import com.omega.jobportal.location.Location;

import java.time.LocalDate;

public record CompanyResponse(
        Long id,
        String companyName,
        String description,
        Long businessStreamId,
        LocalDate establishmentDate,
        Location companyLocation,
        String websiteUrl
) {
}
