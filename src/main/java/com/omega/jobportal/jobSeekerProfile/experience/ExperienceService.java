package com.omega.jobportal.jobSeekerProfile.experience;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfileService;
import com.omega.jobportal.jobSeekerProfile.data.ExperienceRequest;
import com.omega.jobportal.jobSeekerProfile.data.ExperienceResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.ExperienceDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public ExperienceResponse updateExperience(ExperienceRequest request, Long id) {
        AppUser loggedInUser = authenticationService.getSession();
        return experienceRepository.findById(id)
                .map(experience -> {
                    boolean isExperienceOwner = Objects.equals(experience.getJobSeekerProfile().getJobSeeker().getId(), loggedInUser.getId());
                    if (!isExperienceOwner)
                        throw new ApiException("you can only edit your own experience", HttpStatus.FORBIDDEN);
                    Experience updatedExperience = experienceDtoMapper.apply(request, experience);
                    updatedExperience = experienceRepository.save(updatedExperience);
                    return experienceDtoMapper.apply(updatedExperience);
                })
                .orElseThrow(() -> new ApiException("experience detail not found", HttpStatus.NOT_FOUND));
    }

    public List<ExperienceResponse> viewExperiences(Long jobSeekerId) {
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(jobSeekerId);
        return experienceRepository.findExperienceByJobSeekerId(jobSeekerProfile.getJobSeeker().getId())
                .stream()
                .map(experienceDtoMapper)
                .toList();
    }

    public void deleteExperience(Long id) {
        AppUser loggedInUser = authenticationService.getSession();
        experienceRepository.findById(id)
                .ifPresent(experience -> {
                    boolean isExperienceOwner = Objects.equals(experience.getJobSeekerProfile().getJobSeeker().getId(), loggedInUser.getId());
                    if (!isExperienceOwner)
                        throw new ApiException("you can only delete your own experience", HttpStatus.FORBIDDEN);
                    experienceRepository.delete(experience);
                });
    }
}
