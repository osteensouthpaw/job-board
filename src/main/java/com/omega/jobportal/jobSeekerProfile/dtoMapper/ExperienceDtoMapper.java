package com.omega.jobportal.jobSeekerProfile.dtoMapper;

import com.omega.jobportal.jobSeekerProfile.data.ExperienceResponse;
import com.omega.jobportal.jobSeekerProfile.experience.Experience;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ExperienceDtoMapper implements Function<Experience, ExperienceResponse> {
    @Override
    public ExperienceResponse apply(Experience experience) {
        return new ExperienceResponse(
                experience.isCurrentJob(),
                experience.getJobType(),
                experience.getJobTitle(),
                experience.getCompanyName(),
                experience.getJobLocation(),
                experience.getDescription(),
                experience.getStartDate(),
                experience.getEndDate()
        );
    }
}
