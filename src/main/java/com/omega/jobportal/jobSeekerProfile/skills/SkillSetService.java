package com.omega.jobportal.jobSeekerProfile.skills;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfileService;
import com.omega.jobportal.jobSeekerProfile.data.SkillSetRequest;
import com.omega.jobportal.jobSeekerProfile.data.SkillSetResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.SkillSetDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public SkillSetResponse updateSkillSet(SkillSetRequest request, Long skillSetId) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        return skillSetRepository.findById(skillSetId)
                .map(skillSet -> {
                    boolean isSkillSetOwner = skillSet.getJobSeekerProfile().getJobSeeker().getId().equals(jobSeekerProfile.getJobSeeker().getId());
                    if (!isSkillSetOwner) throw new ApiException("you are not skill set owner", HttpStatus.FORBIDDEN);
                    skillSet.setSkill(request.skill());
                    skillSet.setDescription(request.description());
                    SkillSet updatedSkillSet = skillSetRepository.save(skillSet);
                    return skillSetDtoMapper.apply(updatedSkillSet);
                }).orElseThrow(() -> new ApiException("skill set not found", HttpStatus.NOT_FOUND));
    }

    public void deleteSkillSet(Long skillSetId) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        skillSetRepository.findById(skillSetId)
                .ifPresentOrElse(skillSet -> {
                    boolean isSkillSetOwner = skillSet.getJobSeekerProfile().getJobSeeker().getId().equals(jobSeekerProfile.getJobSeeker().getId());
                    if (!isSkillSetOwner) throw new ApiException("you are not skill set owner", HttpStatus.FORBIDDEN);
                    skillSetRepository.delete(skillSet);
                }, () -> {
                    throw new ApiException("skill set not found", HttpStatus.NOT_FOUND);
                });
    }
}
