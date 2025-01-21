package com.omega.jobportal.jobPost;

import com.omega.jobportal.company.Company;
import com.omega.jobportal.company.CompanyService;
import com.omega.jobportal.jobPost.data.JobPostRequest;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.jobPost.dtoMapper.JobPostDtoMapper;
import com.omega.jobportal.location.Location;
import com.omega.jobportal.location.LocationService;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserService;
import com.omega.jobportal.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobPostService {
    private final JobPostRepository jobPostRepository;
    private final UserService userService;
    private final CompanyService companyService;
    private final LocationService locationService;
    private final JobPostDtoMapper jobPostDtoMapper;

    public JobPostResponse createJobPost(JobPostRequest request) {
        //get the id of the user from the session;
        //todo: user must be a recruiter to create a jobPost;
        AppUser user = userService.findUserById(request.recruiterId());
        boolean isRecruiter = user.getUserType().equals(UserType.RECRUITER);
        if (!isRecruiter)
            throw new RuntimeException("only recruiters can create job post");
        Company company = companyService.findCompanyById(request.companyId());
        Location location = locationService.saveLocation(request.location());

        JobPost jobPost = JobPost.builder()
                .recruiter(user)
                .company(company)
                .location(location)
                .jobTitle(request.jobTitle())
                .description(request.jobDescription())
                .jobType(request.jobType())
                .workMode(request.workMode())
                .experienceLevel(request.experienceLevel())
                .build();
        return jobPostDtoMapper.apply(jobPostRepository.save(jobPost));
    }
}
