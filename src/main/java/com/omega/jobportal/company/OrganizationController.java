package com.omega.jobportal.company;

import com.omega.jobportal.company.data.OrganizationRegistrationRequest;
import com.omega.jobportal.company.data.OrganizationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/recruiters/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationResponse> registerOrganization(@RequestBody @Valid OrganizationRegistrationRequest request) {
        OrganizationResponse organizationResponse = organizationService.registerOrganization(request);
        return new ResponseEntity<>(organizationResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponse> updateOrganization(@RequestBody @Valid OrganizationRegistrationRequest request, @PathVariable Long organizationId) {
        OrganizationResponse organizationResponse = organizationService.updateOrganization(request, organizationId);
        return new ResponseEntity<>(organizationResponse, HttpStatus.OK);
    }

}
