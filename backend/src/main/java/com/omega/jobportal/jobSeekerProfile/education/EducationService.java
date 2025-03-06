package com.omega.jobportal.jobSeekerProfile.education;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfileService;
import com.omega.jobportal.jobSeekerProfile.data.EducationRequest;
import com.omega.jobportal.jobSeekerProfile.data.EducationResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.EducationDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;
    private final AuthenticationService authenticationService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final EducationDtoMapper educationDtoMapper;

    public EducationResponse createEducation(EducationRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        Education education = new Education(request, jobSeekerProfile);
        education = educationRepository.save(education);
        return educationDtoMapper.apply(education);
    }

    public void deleteEducation(Long educationId) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        educationRepository.findById(educationId)
                .ifPresentOrElse(education -> {
                    boolean isEducationOwner = Objects.equals(education.getJobSeekerProfile().getJobSeeker().getId(), jobSeekerProfile.getJobSeeker().getId());
                    if (!isEducationOwner) throw new ApiException("you are not education owner", HttpStatus.FORBIDDEN);
                    educationRepository.delete(education);
                }, () -> {
                    throw new ApiException("education detail not found", HttpStatus.NOT_FOUND);
                });
    }

    public List<EducationResponse> viewEducationDetails(Long id) {
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(id);
        return educationRepository.findEducationByJobSeekerId(jobSeekerProfile.getJobSeeker().getId())
                .stream().map(educationDtoMapper)
                .toList();
    }

    public EducationResponse editEducationDetail(EducationRequest request, Long educationDetailId) {
        AppUser loggedInUser = authenticationService.getSession();
        return educationRepository.findById(educationDetailId)
                .map(education -> {
                    boolean isEducationOwner = Objects.equals(
                            loggedInUser.getId(),
                            education.getJobSeekerProfile().getJobSeeker().getId()
                    );
                    if (!isEducationOwner)
                        throw new ApiException("you are not education owner", HttpStatus.FORBIDDEN);
                    Education updatedEducation = educationDtoMapper.apply(request, education);
                    Education savedUpdatedEducation = educationRepository.save(updatedEducation);
                    return educationDtoMapper.apply(savedUpdatedEducation);
                })
                .orElseThrow(() -> new ApiException("Education detail not found", HttpStatus.NOT_FOUND));
    }
}
