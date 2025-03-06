package com.omega.jobportal.company.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record OrganizationRegistrationRequest(
        @NotBlank
        @Size(min = 4)
        String companyName,

        @NotBlank
        @Size(min = 50)
        String description,

        @NotNull
        Long businessStream,

        @PastOrPresent
        LocalDate establishmentDate,

        @NotBlank
        String companyLocation,

        String websiteUrl
) {
}
