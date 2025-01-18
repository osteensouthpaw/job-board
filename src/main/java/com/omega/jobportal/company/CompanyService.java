package com.omega.jobportal.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        //TODO: refine company data
        return companyRepository.save(company);
    }

    public Company findCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Company not found"));
    }
}
