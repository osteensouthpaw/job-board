package com.omega.jobportal.jobSeekerProfile.dtoMapper;

import com.omega.jobportal.jobSeekerProfile.data.SkillSetResponse;
import com.omega.jobportal.jobSeekerProfile.skills.SkillSet;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SkillSetDtoMapper implements Function<SkillSet, SkillSetResponse> {
    @Override
    public SkillSetResponse apply(SkillSet skillSet) {
        return new SkillSetResponse(
                skillSet.getId(),
                skillSet.getJobSeekerProfile().getJobSeeker().getId(),
                skillSet.getSkill(),
                skillSet.getDescription()
        );
    }
}
