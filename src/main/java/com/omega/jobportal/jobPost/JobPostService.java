package com.omega.jobportal.jobPost;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.company.Company;
import com.omega.jobportal.company.CompanyService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobPost.data.JobPostRequest;
import com.omega.jobportal.jobPost.data.JobPostResponse;
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
        boolean isRecruiter = recruiter.getUserType().equals(UserType.RECRUITER);

        if (!isRecruiter)
            throw new ApiException("only recruiters can create job post", HttpStatus.BAD_REQUEST);

        Company company = companyService.findCompanyById(request.companyId());
        Location location = locationService.saveLocation(request.location());

        JobPost jobPost = new JobPost(request, recruiter, company, location);
        return jobPostDtoMapper.apply(jobPostRepository.save(jobPost));
    }
}
