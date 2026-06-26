package com.omega.jobportal.company;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.company.businessStream.BusinessStream;
import com.omega.jobportal.company.businessStream.BusinessStreamService;
import com.omega.jobportal.company.data.OrganizationRegistrationRequest;
import com.omega.jobportal.company.data.OrganizationResponse;
import com.omega.jobportal.company.dtoMapper.OrganizationDtoMapper;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobPost.JobPostRepository;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.jobPost.dtoMapper.JobPostDtoMapper;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final BusinessStreamService businessStreamService;
    private final OrganizationDtoMapper organizationDtoMapper;
    private final AuthenticationService authenticationService;
    private final JobPostRepository jobPostRepository;
    private final JobPostDtoMapper jobPostDtoMapper;

    public OrganizationResponse registerOrganization(OrganizationRegistrationRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        BusinessStream businessStream = businessStreamService.findById(request.businessStreamId());
        Organization organization = new Organization(request, businessStream, loggedInUser);
        return organizationDtoMapper.apply(organization);
    }

    public Organization findOrganizationByRecruiterId(Long recruiterId) {
        return organizationRepository.findOrganizationByRecruiterId(recruiterId)
                .orElseThrow(() -> new ApiException("cannot find organization with that id", HttpStatus.NOT_FOUND));
    }

    public OrganizationResponse updateOrganization(OrganizationRegistrationRequest request, Long companyId) {
        Organization organization = findOrganizationById(companyId);
        BusinessStream businessStream = businessStreamService.findById(request.businessStreamId());
        organization = organizationDtoMapper.apply(request, organization, businessStream);
        return organizationDtoMapper.apply(organizationRepository.save(organization));
    }

    public Organization findOrganizationById(Long id) {
        return organizationRepository.findById(id).orElseThrow(() ->
                new ApiException("Company not found", HttpStatus.NOT_FOUND));
    }

    public PageResponse<JobPostResponse> findJobPostsByOrganizationId(Long organizationId, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<JobPostResponse> jobPosts = jobPostRepository
                .findJobPostsByOrganizationId(organizationId, pageRequest)
                .map(jobPostDtoMapper);
        return new PageResponse<>(jobPosts);
    }
}
