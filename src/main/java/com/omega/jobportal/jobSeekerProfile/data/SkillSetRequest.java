package com.omega.jobportal.jobSeekerProfile.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SkillSetRequest(
        @NotBlank
        String skill,

        @Size(max = 500)
        String description
) {
}
