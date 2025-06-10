package com.omega.jobportal.jobSeekerProfile.dtoMapper;

import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileRequest;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JobSeekerProfileDtoMapper implements Function<JobSeekerProfile, JobSeekerProfileResponse> {
    private final UserDtoMapper userDtoMapper;
    private final ExperienceDtoMapper experienceDtoMapper;
    private final EducationDtoMapper educationDtoMapper;
    private final SkillSetDtoMapper skillSetDtoMapper;

    @Override
    public JobSeekerProfileResponse apply(JobSeekerProfile jobSeekerProfile) {
        return new JobSeekerProfileResponse(
                jobSeekerProfile.getId(),
                userDtoMapper.apply(jobSeekerProfile.getJobSeeker()),
                jobSeekerProfile.getCurrentAnnualSalary(),
                jobSeekerProfile.getBio(),
                jobSeekerProfile.getProfession(),
                jobSeekerProfile.getPersonalWebsiteUrl(),
                jobSeekerProfile.getLinkedInUrl(),
                jobSeekerProfile.getGitHubUrl(),
                jobSeekerProfile.getTwitterUrl(),
                jobSeekerProfile.getDateOfBirth(),
                jobSeekerProfile.getExperienceLevel(),
                jobSeekerProfile.getExperiences().stream().map(experienceDtoMapper).toList(),
                jobSeekerProfile.getSkillSets().stream().map(skillSetDtoMapper).toList(),
                jobSeekerProfile.getEducations().stream().map(educationDtoMapper).toList()
        );
    }

    public JobSeekerProfile apply(JobSeekerProfileRequest request, JobSeekerProfile profile) {
        profile.setBio(request.bio());
        profile.setPhone(request.phone());
        profile.setProfession(request.profession());
        profile.setPersonalWebsiteUrl(request.personalWebsiteUrl());
        profile.setLinkedInUrl(request.linkedInUrl());
        profile.setGitHubUrl(request.gitHubUrl());
        profile.setTwitterUrl(request.twitterUrl());
        profile.setDateOfBirth(request.dateOfBirth());
        profile.setExperienceLevel(request.experienceLevel());
        profile.setCurrentAnnualSalary(request.currentAnnualSalary());
        return profile;
    }
}
