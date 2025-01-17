package com.omega.jobportal.jobPost;

import com.omega.jobportal.jobPost.data.JobPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobPostService {
    private final JobPostRepository jobPostRepository;

    public JobPost createJobPost(JobPostRequest jobPostRequest) {

        return null;
    }
}
