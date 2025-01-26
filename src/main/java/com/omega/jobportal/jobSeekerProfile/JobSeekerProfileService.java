package com.omega.jobportal.jobSeekerProfile;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileRequest;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.JobSeekerProfileDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
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
}
