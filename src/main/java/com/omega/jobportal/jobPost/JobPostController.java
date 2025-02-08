package com.omega.jobportal.jobPost;

import com.omega.jobportal.jobPost.data.JobPostRequest;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.jobPost.data.JobPostUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<JobPostResponse>> findAllJobs() {
        List<JobPostResponse> jobPosts = jobPostService.findAllJobs();
        return new ResponseEntity<>(jobPosts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostResponse> findById(@PathVariable Long id) {
        JobPostResponse jobPost = jobPostService.findJobById(id);
        return new ResponseEntity<>(jobPost, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JobPostResponse> updateJobPost(@RequestBody @Valid JobPostUpdateRequest request, @PathVariable Long id) {
        JobPostResponse jobPost = jobPostService.updateJobPost(request, id);
        return new ResponseEntity<>(jobPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPost(@PathVariable Long id) {
        jobPostService.deleteJobPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobPostResponse>> searchJobs(@RequestParam(name = "searchQuery") String searchQuery) {
        List<JobPostResponse> jobPosts = jobPostService.searchJobPosts(searchQuery);
        return new ResponseEntity<>(jobPosts, HttpStatus.OK);
    }
}
