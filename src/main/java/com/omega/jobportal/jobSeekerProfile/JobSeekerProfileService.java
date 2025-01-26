package com.omega.jobportal.jobSeekerProfile;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileRequest;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.JobSeekerProfileDtoMapper;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileService {
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final AuthenticationService authenticationService;
    private final UserDtoMapper userDtoMapper;
    private final JobSeekerProfileDtoMapper jobSeekerProfileDtoMapper;

    public JobSeekerProfileResponse createProfile(JobSeekerProfileRequest request) {
        UserResponse loggedInUser = authenticationService.getSession();
        AppUser user = userDtoMapper.apply(loggedInUser);
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile(request, user);
        jobSeekerProfile = jobSeekerProfileRepository.save(jobSeekerProfile);
        return jobSeekerProfileDtoMapper.apply(jobSeekerProfile);
    }
}
