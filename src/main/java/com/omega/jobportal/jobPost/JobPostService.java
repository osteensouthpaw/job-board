package com.omega.jobportal.jobPost;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.company.Company;
import com.omega.jobportal.company.CompanyService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobPost.data.JobPostRequest;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.jobPost.data.JobPostUpdateRequest;
import com.omega.jobportal.jobPost.dtoMapper.JobPostDtoMapper;
import com.omega.jobportal.location.Location;
import com.omega.jobportal.location.LocationService;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserType;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JobPostService {
    private final JobPostRepository jobPostRepository;
    private final AuthenticationService authService;
    private final CompanyService companyService;
    private final LocationService locationService;
    private final JobPostDtoMapper jobPostDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public JobPostResponse createJobPost(JobPostRequest request) {
        UserResponse authenticatedUser = authService.getSession();
        AppUser recruiter = userDtoMapper.apply(authenticatedUser);
        Company company = companyService.findCompanyById(request.companyId());
        Location location = locationService.saveLocation(request.location());
        JobPost jobPost = new JobPost(request, recruiter, company, location);
        return jobPostDtoMapper.apply(jobPostRepository.save(jobPost));
    }

    public void deleteJobPost(Long jobPostId) {
        UserResponse authenticatedUser = authService.getSession();
        AppUser recruiter = userDtoMapper.apply(authenticatedUser);
        boolean isRecruiter = recruiter.getUserType().equals(UserType.RECRUITER);

        if (!isRecruiter)
            throw new ApiException("only recruiters can delete a post", HttpStatus.BAD_REQUEST);

        jobPostRepository.findById(jobPostId)
                .ifPresentOrElse(jobPost -> {
                    boolean isJobPostOwner = Objects.equals(jobPost.getRecruiter().getId(), recruiter.getId());
                    if (!isJobPostOwner)
                        throw new ApiException("only jobPost publisher are allowed to delete their posts", HttpStatus.FORBIDDEN);
                    jobPostRepository.deleteById(jobPostId);

                }, () -> {
                    throw new ApiException("no such job post", HttpStatus.NOT_FOUND);
                });
    }

    public JobPostResponse updateJobPost(JobPostUpdateRequest request, Long jobPostId) {
        UserResponse loggedInUser = authService.getSession();
        JobPost jobPost = findJobPostById(jobPostId);
        boolean isJobPostPublisher = Objects.equals(jobPost.getRecruiter().getId(), loggedInUser.id());
        if (!isJobPostPublisher)
            throw new ApiException("only jobPost owners can update a post", HttpStatus.FORBIDDEN);
        Location location = locationService.updateLocation(jobPost.getLocation().getId(), request.location());
        JobPost updatedJobPost = jobPostDtoMapper.apply(request, jobPost, location);
        updatedJobPost = jobPostRepository.save(updatedJobPost);
        return jobPostDtoMapper.apply(updatedJobPost);
    }

    public JobPost findJobPostById(Long id) {
        return jobPostRepository.findById(id)
                .orElseThrow(() -> new ApiException("no such job post", HttpStatus.NOT_FOUND));
    }
}
