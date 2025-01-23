package com.omega.jobportal.jobApplication.dtoMapper;

import com.omega.jobportal.jobApplication.JobApplication;
import com.omega.jobportal.jobApplication.data.JobApplicationResponse;
import com.omega.jobportal.jobPost.dtoMapper.JobPostDtoMapper;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JobApplicationDtoMapper implements Function<JobApplication, JobApplicationResponse> {
    private final UserDtoMapper userDtoMapper;
    private final JobPostDtoMapper jobPostDtoMapper;

    @Override
    public JobApplicationResponse apply(JobApplication application) {
        return new JobApplicationResponse(
                userDtoMapper.apply(application.getId().getApplicant()),
                jobPostDtoMapper.apply(application.getId().getJobPost()),
                application.getApplicationDate());
    }
}
