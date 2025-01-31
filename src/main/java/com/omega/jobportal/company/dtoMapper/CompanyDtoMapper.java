package com.omega.jobportal.company.dtoMapper;

import com.omega.jobportal.company.Company;
import com.omega.jobportal.company.businessStream.BusinessStream;
import com.omega.jobportal.company.data.CompanyRegistrationRequest;
import com.omega.jobportal.company.data.CompanyResponse;
import com.omega.jobportal.location.Location;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CompanyDtoMapper implements Function<Company, CompanyResponse> {
    @Override
    public CompanyResponse apply(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getCompanyName(),
                company.getDescription(),
                company.getBusinessStream().getId(),
                company.getEstablishmentDate(),
                company.getCompanyLocation(),
                company.getWebsiteUrl()
        );
    }

    public Company apply(CompanyRegistrationRequest request, Company company, Location location, BusinessStream businessStream) {
        company.setWebsiteUrl(request.websiteUrl());
        company.setCompanyLocation(location);
        company.setEstablishmentDate(request.establishmentDate());
        company.setDescription(request.description());
        company.setCompanyName(request.companyName());
        company.setBusinessStream(businessStream);
        return company;
    }
}
