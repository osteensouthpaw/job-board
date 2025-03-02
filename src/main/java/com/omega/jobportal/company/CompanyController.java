package com.omega.jobportal.company;

import com.omega.jobportal.company.data.CompanyRegistrationRequest;
import com.omega.jobportal.company.data.CompanyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/recruiters/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> registerCompany(@RequestBody @Valid CompanyRegistrationRequest request) {
        CompanyResponse companyResponse = companyService.registerCompany(request);
        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> updateCompany(@RequestBody @Valid CompanyRegistrationRequest request, @PathVariable Long companyId) {
        CompanyResponse companyResponse = companyService.updateCompany(request, companyId);
        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

}
