package com.omega.jobportal.jobPost.dtoMapper;

import com.omega.jobportal.company.dtoMapper.OrganizationDtoMapper;
import com.omega.jobportal.jobPost.JobPost;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.jobPost.data.JobPostUpdateRequest;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JobPostDtoMapper implements Function<JobPost, JobPostResponse> {
    private final UserDtoMapper userDtoMapper;
    private final OrganizationDtoMapper organizationDtoMapper;

    @Override
    public JobPostResponse apply(JobPost jobPost) {
        boolean isApplicationOpen = LocalDateTime.now().isBefore(jobPost.getApplicationDeadline());
        return new JobPostResponse(
                jobPost.getId(),
                userDtoMapper.apply(jobPost.getRecruiter()),
                organizationDtoMapper.apply(jobPost.getOrganization()),
                jobPost.getLocation(),
                jobPost.getJobTitle(),
                jobPost.getDescription(),
                jobPost.getSalary(),
                jobPost.getJobType(),
                jobPost.getWorkMode(),
                jobPost.getExperienceLevel(),
                isApplicationOpen,
                jobPost.getApplicationDeadline(),
                jobPost.getCreatedAt()
        );
    }

    public JobPost apply(JobPostUpdateRequest request, JobPost jobPost) {
        jobPost.setJobTitle(request.jobTitle());
        jobPost.setDescription(request.jobDescription());
        jobPost.setSalary(request.hourlyRate());
        jobPost.setApplicationDeadline(request.applicationDeadline());
        jobPost.setExperienceLevel(request.experienceLevel());
        jobPost.setJobType(request.jobType());
        jobPost.setWorkMode(request.workMode());
        jobPost.setLocation(request.location());
        return jobPost;
    }
}
