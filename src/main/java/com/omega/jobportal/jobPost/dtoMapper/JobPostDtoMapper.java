package com.omega.jobportal.jobPost.dtoMapper;

import com.omega.jobportal.jobPost.JobPost;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobPostDtoMapper implements Function<JobPost, JobPostResponse> {
    private final UserDtoMapper userDtoMapper;

    public JobPostDtoMapper(UserDtoMapper userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public JobPostResponse apply(JobPost jobPost) {
        boolean isApplicationOpen = LocalDateTime.now().isBefore(jobPost.getApplicationDeadline());
        return new JobPostResponse(
                jobPost.getId(),
                userDtoMapper.apply(jobPost.getRecruiter()),
                jobPost.getCompany(),
                jobPost.getLocation(),
                jobPost.getJobTitle(),
                jobPost.getDescription(),
                jobPost.getJobType(),
                jobPost.getWorkMode(),
                jobPost.getExperienceLevel(),
                isApplicationOpen,
                jobPost.getApplicationDeadline(),
                jobPost.getCreatedAt()
        );
    }
}
