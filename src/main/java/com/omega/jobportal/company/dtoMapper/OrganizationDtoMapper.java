package com.omega.jobportal.company.dtoMapper;

import com.omega.jobportal.company.Organization;
import com.omega.jobportal.company.businessStream.BusinessStream;
import com.omega.jobportal.company.data.OrganizationRegistrationRequest;
import com.omega.jobportal.company.data.OrganizationResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OrganizationDtoMapper implements Function<Organization, OrganizationResponse> {
    @Override
    public OrganizationResponse apply(Organization organization) {
        return new OrganizationResponse(
                organization.getId(),
                organization.getOrganizationName(),
                organization.getDescription(),
                organization.getBusinessStream().getId(),
                organization.getEstablishmentDate(),
                organization.getLocation(),
                organization.getWebsiteUrl()
        );
    }

    public Organization apply(OrganizationRegistrationRequest request, Organization organization, BusinessStream businessStream) {
        organization.setWebsiteUrl(request.websiteUrl());
        organization.setLocation(request.companyLocation());
        organization.setEstablishmentDate(request.establishmentDate());
        organization.setDescription(request.description());
        organization.setOrganizationName(request.companyName());
        organization.setBusinessStream(businessStream);
        return organization;
    }
}
