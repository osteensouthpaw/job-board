package com.omega.jobportal.company.dtoMapper;

import com.omega.jobportal.company.Company;
import com.omega.jobportal.company.data.CompanyResponse;
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
}
