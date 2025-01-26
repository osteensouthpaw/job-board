package com.omega.jobportal.jobSeekerProfile.dtoMapper;

import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class JobSeekerProfileDtoMapper implements Function<JobSeekerProfile, JobSeekerProfileResponse> {
    @Override
    public JobSeekerProfileResponse apply(JobSeekerProfile jobSeekerProfile) {
        return new JobSeekerProfileResponse(
                jobSeekerProfile.getId(),
                jobSeekerProfile.getJobSeeker().getId(),
                jobSeekerProfile.getCurrentAnnualSalary(),
                jobSeekerProfile.getBio(),
                jobSeekerProfile.getProfession(),
                jobSeekerProfile.getPersonalWebsiteUrl(),
                jobSeekerProfile.getLinkedInUrl(),
                jobSeekerProfile.getGitHubUrl(),
                jobSeekerProfile.getTwitterUrl(),
                jobSeekerProfile.getDateOfBirth(),
                jobSeekerProfile.getExperienceLevel()
        );

    }
}
