package com.omega.jobportal.jobApplication;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobApplication.data.JobApplicationResponse;
import com.omega.jobportal.jobApplication.dtoMapper.JobApplicationDtoMapper;
import com.omega.jobportal.jobApplication.key.JobApplicationKey;
import com.omega.jobportal.jobPost.JobPost;
import com.omega.jobportal.jobPost.JobPostService;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruiterApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final AuthenticationService authenticationService;
    private final JobApplicationDtoMapper jobApplicationDtoMapper;
    private final UserService userService;
    private final JobPostService jobPostService;

    public List<JobApplicationResponse> findJobPostApplicationsByJobPostId(Long jobPostId) {
        AppUser loggedInUser = authenticationService.getSession();
        return jobApplicationRepository
                .findJobPostApplicationsById(jobPostId, loggedInUser.getId())
                .stream().map(jobApplicationDtoMapper)
                .toList();
    }

    public void acceptApplication(Long jobPostId, Long applicantId) {
        AppUser applicant = userService.findUserById(applicantId);
        JobPost jobPost = jobPostService.findJobPostById(jobPostId);
        JobApplicationKey jobApplicationKey = new JobApplicationKey(applicant, jobPost);
        jobApplicationRepository.findById(jobApplicationKey)
                .ifPresentOrElse(application -> {
                    application.setApplicationStatus(ApplicationStatus.ACCEPTED);
                    jobApplicationRepository.save(application);
                    //todo: send confirmation email
                }, () -> {
                    throw new ApiException("Job application not found", HttpStatus.NOT_FOUND);
                });
    }

    public void rejectApplication(Long jobPostId, Long applicantId) {
        AppUser applicant = userService.findUserById(applicantId);
        JobPost jobPost = jobPostService.findJobPostById(jobPostId);
        JobApplicationKey jobApplicationKey = new JobApplicationKey(applicant, jobPost);
        jobApplicationRepository.findById(jobApplicationKey)
                .ifPresentOrElse(application -> {
                    application.setApplicationStatus(ApplicationStatus.REJECTED);
                    jobApplicationRepository.save(application);
                    //todo: send confirmation email
                }, () -> {
                    throw new ApiException("Job application not found", HttpStatus.NOT_FOUND);
                });
    }
}
