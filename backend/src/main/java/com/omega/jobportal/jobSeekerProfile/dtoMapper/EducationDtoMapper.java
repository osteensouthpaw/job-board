package com.omega.jobportal.jobSeekerProfile.dtoMapper;

import com.omega.jobportal.jobSeekerProfile.data.EducationRequest;
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

    //update education: I used to the same dto for request since they share all the same details
    public Education apply(EducationRequest request, Education education) {
        education.setEducationLevel(request.educationLevel());
        education.setFieldOfStudy(request.fieldOfStudy());
        education.setInstitution(request.institution());
        education.setStartDate(request.startDate());
        education.setEndDate(request.endDate());
        education.setCertificateUrl(request.certificateUrl());
        return education;
    }
}
