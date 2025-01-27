package com.omega.jobportal.jobSeekerProfile;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileRequest;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.JobSeekerProfileDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileService {
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final AuthenticationService authenticationService;
    private final JobSeekerProfileDtoMapper jobSeekerProfileDtoMapper;

    public JobSeekerProfileResponse createProfile(JobSeekerProfileRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile(request, loggedInUser);
        jobSeekerProfile = jobSeekerProfileRepository.save(jobSeekerProfile);
        return jobSeekerProfileDtoMapper.apply(jobSeekerProfile);
    }

    public JobSeekerProfileResponse viewJobSeekerProfile() {
        AppUser loggedInUser = authenticationService.getSession();
        return jobSeekerProfileRepository.findJobSeekerProfileByJobSeekerId(loggedInUser.getId())
                .map(jobSeekerProfileDtoMapper)
                .orElseThrow(() -> new ApiException("user profile not found", HttpStatus.NOT_FOUND));
    }

    public JobSeekerProfileResponse updateJobSeekerProfile(JobSeekerProfileRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        return jobSeekerProfileRepository.findJobSeekerProfileByJobSeekerId(loggedInUser.getId())
                .map(profile -> {
                    JobSeekerProfile updatedProfile = jobSeekerProfileDtoMapper.apply(request, profile);
                    updatedProfile = jobSeekerProfileRepository.save(updatedProfile);
                    return jobSeekerProfileDtoMapper.apply(updatedProfile);
                })
                .orElseThrow(() -> new ApiException("user profile not found", HttpStatus.NOT_FOUND));
    }

    public JobSeekerProfile findJobSeekerProfileByJobSeekerId(Long id) {
        return jobSeekerProfileRepository.findJobSeekerProfileByJobSeekerId(id)
                .orElseThrow(() -> new ApiException("user profile not found", HttpStatus.NOT_FOUND));
    }
}
