package com.omega.jobportal.jobSeekerProfile.dtoMapper;

import com.omega.jobportal.jobSeekerProfile.data.EducationResponse;
import com.omega.jobportal.jobSeekerProfile.education.Education;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EducationDtoMapper implements Function<Education, EducationResponse> {
    @Override
    public EducationResponse apply(Education education) {
        return new EducationResponse(
                education.getId(),
                education.getJobSeekerProfile().getJobSeeker().getId(),
                education.getEducationLevel(),
                education.getFieldOfStudy(),
                education.getInstitution(),
                education.getStartDate(),
                education.getEndDate(),
                education.getCertificateUrl()
        );
    }
}
