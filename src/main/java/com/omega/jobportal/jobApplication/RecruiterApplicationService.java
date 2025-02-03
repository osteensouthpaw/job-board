package com.omega.jobportal.jobApplication;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.jobApplication.data.JobApplicationResponse;
import com.omega.jobportal.jobApplication.dtoMapper.JobApplicationDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruiterApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final AuthenticationService authenticationService;
    private final JobApplicationDtoMapper jobApplicationDtoMapper;

    public List<JobApplicationResponse> findJobPostApplicationsByJobPostId(Long jobPostId) {
        AppUser loggedInUser = authenticationService.getSession();
        return jobApplicationRepository
                .findJobPostApplicationsById(jobPostId, loggedInUser.getId())
                .stream().map(jobApplicationDtoMapper)
                .toList();
    }
}
