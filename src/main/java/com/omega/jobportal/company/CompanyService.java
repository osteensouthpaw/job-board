package com.omega.jobportal.company;

import com.omega.jobportal.company.businessStream.BusinessStream;
import com.omega.jobportal.company.businessStream.BusinessStreamService;
import com.omega.jobportal.company.data.CompanyRegistrationRequest;
import com.omega.jobportal.company.data.CompanyResponse;
import com.omega.jobportal.company.dtoMapper.CompanyDtoMapper;
import com.omega.jobportal.location.Location;
import com.omega.jobportal.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final LocationService locationService;
    private final BusinessStreamService businessStreamService;
    private final CompanyDtoMapper companyDtoMapper;

    public CompanyResponse registerCompany(CompanyRegistrationRequest request) {
        Location location = locationService.saveLocation(request.companyLocation());
        BusinessStream businessStream = businessStreamService.findById(request.businessStream());
        Company company = new Company(request, location, businessStream);
        return companyDtoMapper.apply(company);
    }

    public Company findCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Company not found"));
    }
}
