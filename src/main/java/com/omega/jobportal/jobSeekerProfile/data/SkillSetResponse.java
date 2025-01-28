package com.omega.jobportal.jobSeekerProfile.data;

public record SkillSetResponse(
        Long id,
        Long jobSeekerId,
        String skill,
        String description
) {
}
