package com.omega.jobportal.jobSeekerProfile.education;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfileService;
import com.omega.jobportal.jobSeekerProfile.data.EducationRequest;
import com.omega.jobportal.jobSeekerProfile.data.EducationResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.EducationDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;
    private final AuthenticationService authenticationService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final EducationDtoMapper educationDtoMapper;

    public EducationResponse createEducation(EducationRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        Education education = new Education(request, jobSeekerProfile);
        education = educationRepository.save(education);
        return educationDtoMapper.apply(education);
    }
}
