package com.omega.jobportal.jobSeekerProfile.skills;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfileService;
import com.omega.jobportal.jobSeekerProfile.data.SkillSetRequest;
import com.omega.jobportal.jobSeekerProfile.data.SkillSetResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.SkillSetDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillSetService {
    private final SkillSetRepository skillSetRepository;
    private final AuthenticationService authenticationService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final SkillSetDtoMapper skillSetDtoMapper;

    public SkillSetResponse saveSkillSet(SkillSetRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        SkillSet skillSet = new SkillSet(request, jobSeekerProfile);
        skillSet = skillSetRepository.save(skillSet);
        return skillSetDtoMapper.apply(skillSet);
    }
}
