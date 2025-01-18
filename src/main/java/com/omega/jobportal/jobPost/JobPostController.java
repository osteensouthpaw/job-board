package com.omega.jobportal.jobPost;

import com.omega.jobportal.jobPost.data.JobPostRequest;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/job-posts")
@RequiredArgsConstructor
public class JobPostController {
    private final JobPostService jobPostService;

    @PostMapping
    public ResponseEntity<JobPostResponse> createJobPost(@RequestBody JobPostRequest request) {
        JobPostResponse jobPostResponse = jobPostService.createJobPost(request);
        return new ResponseEntity<>( jobPostResponse, HttpStatus.CREATED);
    }
}
