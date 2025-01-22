package com.omega.jobportal.jobPost;

import com.omega.jobportal.jobPost.data.JobPostRequest;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/job-posts")
@RequiredArgsConstructor
public class JobPostController {
    private final JobPostService jobPostService;

    @PostMapping
    public ResponseEntity<JobPostResponse> createJobPost(@RequestBody @Valid JobPostRequest request) {
        JobPostResponse jobPostResponse = jobPostService.createJobPost(request);
        return new ResponseEntity<>(jobPostResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPost(@PathVariable Long id) {
        jobPostService.deleteJobPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
