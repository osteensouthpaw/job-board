package com.omega.jobportal.jobSeekerProfile.experience;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfileService;
import com.omega.jobportal.jobSeekerProfile.data.ExperienceRequest;
import com.omega.jobportal.jobSeekerProfile.data.ExperienceResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.ExperienceDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final AuthenticationService authenticationService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final ExperienceDtoMapper experienceDtoMapper;

    public ExperienceResponse saveExperience(ExperienceRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        Experience experience = new Experience(request, jobSeekerProfile);
        experience = experienceRepository.save(experience);
        return experienceDtoMapper.apply(experience);
    }
}
