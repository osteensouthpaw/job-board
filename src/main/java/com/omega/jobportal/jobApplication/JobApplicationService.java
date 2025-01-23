package com.omega.jobportal.jobApplication;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobApplication.data.JobApplicationRequest;
import com.omega.jobportal.jobApplication.data.JobApplicationResponse;
import com.omega.jobportal.jobApplication.dtoMapper.JobApplicationDtoMapper;
import com.omega.jobportal.jobApplication.key.JobApplicationKey;
import com.omega.jobportal.jobPost.JobPost;
import com.omega.jobportal.jobPost.JobPostService;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserType;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final AuthenticationService authenticationService;
    private final UserDtoMapper userDtoMapper;
    private final JobPostService jobPostService;
    private final JobApplicationDtoMapper jobApplicationDtoMapper;

    public JobApplicationResponse createJobApplication(JobApplicationRequest request) {
        AppUser applicant = validatedApplicant();
        var jobPost = jobPostService.findJobPostById(request.jobPostId());

        if (!jobPost.isActive())
            throw new ApiException("job application closed, applications no longer accepted", HttpStatus.BAD_REQUEST);
        JobApplicationKey jobApplicationKey = new JobApplicationKey(applicant, jobPost);

        return jobApplicationRepository.findById(jobApplicationKey)
                .map(jobApplicationDtoMapper)
                .orElseGet(() -> {
                    JobApplication jobApplication = new JobApplication(jobApplicationKey);
                    JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);
                    return jobApplicationDtoMapper.apply(savedJobApplication);
                });
    }

    public void deleteJobApplication(Long jobPostId) {
        AppUser applicant = validatedApplicant();
        JobPost jobPost = jobPostService.findJobPostById(jobPostId);
        JobApplicationKey jobApplicationId = new JobApplicationKey(applicant, jobPost);
        jobApplicationRepository.findById(jobApplicationId)
                .ifPresentOrElse(jobApplicationRepository::delete, () -> {
                    throw new ApiException("job application not found", HttpStatus.NOT_FOUND);
                });
    }

    public List<JobApplicationResponse> findAllApplications() {
        return jobApplicationRepository.findAll()
                .stream()
                .map(jobApplicationDtoMapper)
                .toList();
    }

    private AppUser validatedApplicant() {
        UserResponse authenticatedUser = authenticationService.getSession();
        AppUser applicant = userDtoMapper.apply(authenticatedUser);
        boolean isJobSeeker = applicant.getUserType().equals(UserType.JOB_SEEKER);
        if (!isJobSeeker)
            throw new ApiException("applicant must be a job seeker", HttpStatus.BAD_REQUEST);
        return applicant;
    }
}
