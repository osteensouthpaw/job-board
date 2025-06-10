package com.omega.jobportal.jobSeekerProfile;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileRequest;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.JobSeekerProfileDtoMapper;
import com.omega.jobportal.searching.SearchService;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JobSeekerProfileService {
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final AuthenticationService authenticationService;
    private final JobSeekerProfileDtoMapper jobSeekerProfileDtoMapper;
    private final SearchService searchService;

    public JobSeekerProfileResponse createProfile(JobSeekerProfileRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile(request, loggedInUser);
        jobSeekerProfile = jobSeekerProfileRepository.save(jobSeekerProfile);
        return jobSeekerProfileDtoMapper.apply(jobSeekerProfile);
    }

    public JobSeekerProfileResponse viewJobSeekerProfile(Long userId) {
        return jobSeekerProfileRepository.findJobSeekerProfileByJobSeekerId(userId)
                .map(jobSeekerProfileDtoMapper)
                .orElseThrow(() -> new ApiException("user profile not found", HttpStatus.NOT_FOUND));
    }

    public JobSeekerProfileResponse updateJobSeekerProfile(JobSeekerProfileRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        return jobSeekerProfileRepository.findJobSeekerProfileByJobSeekerId(loggedInUser.getId())
                .map(profile -> {
                    JobSeekerProfile updatedProfile = jobSeekerProfileDtoMapper.apply(request, profile);
                    updatedProfile = jobSeekerProfileRepository.save(updatedProfile);
                    return jobSeekerProfileDtoMapper.apply(updatedProfile);
                })
                .orElseThrow(() -> new ApiException("user profile not found", HttpStatus.NOT_FOUND));
    }

    public JobSeekerProfile findJobSeekerProfileByJobSeekerId(Long id) {
        return jobSeekerProfileRepository.findJobSeekerProfileByJobSeekerId(id)
                .orElseThrow(() -> new ApiException("user profile not found", HttpStatus.NOT_FOUND));
    }

    public PageResponse<JobSeekerProfileResponse> searchJobSeekers(String searchQuery, int page, int size) {
        var searchResults = searchService.search(
                JobSeekerProfile.class, searchQuery, page, size, List.of("bio"), jobSeekerProfileDtoMapper
        );
        return new PageResponse<>(searchResults);
    }
}
