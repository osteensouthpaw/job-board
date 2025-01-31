package com.omega.jobportal.company;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.company.businessStream.BusinessStream;
import com.omega.jobportal.company.businessStream.BusinessStreamService;
import com.omega.jobportal.company.data.CompanyRegistrationRequest;
import com.omega.jobportal.company.data.CompanyResponse;
import com.omega.jobportal.company.dtoMapper.CompanyDtoMapper;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.location.Location;
import com.omega.jobportal.location.LocationService;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final LocationService locationService;
    private final BusinessStreamService businessStreamService;
    private final CompanyDtoMapper companyDtoMapper;
    private final AuthenticationService authenticationService;

    public CompanyResponse registerCompany(CompanyRegistrationRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        Location location = locationService.saveLocation(request.companyLocation());
        BusinessStream businessStream = businessStreamService.findById(request.businessStream());
        Company company = new Company(request, location, businessStream, loggedInUser);
        return companyDtoMapper.apply(company);
    }

    public CompanyResponse updateCompany(CompanyRegistrationRequest request, Long companyId) {
        Company company = findCompanyById(companyId);
        Location location = locationService.updateLocation(company.getCompanyLocation().getId(), request.companyLocation());
        BusinessStream businessStream = businessStreamService.findById(request.businessStream());
        company = companyDtoMapper.apply(request, company, location, businessStream);
        return companyDtoMapper.apply(companyRepository.save(company));
    }

    public Company findCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() ->
                new ApiException("Company not found", HttpStatus.NOT_FOUND));
    }
}
