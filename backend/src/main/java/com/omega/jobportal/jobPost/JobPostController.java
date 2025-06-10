package com.omega.jobportal.jobPost;

import com.omega.jobportal.jobPost.data.JobPostFilterQuery;
import com.omega.jobportal.jobPost.data.JobPostRequest;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.jobPost.data.JobPostUpdateRequest;
import com.omega.jobportal.utils.PageResponse;
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

    @GetMapping
    public ResponseEntity<PageResponse<JobPostResponse>>
    findJobPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                 @RequestParam(value = "size", defaultValue = "20") int size,
                 JobPostFilterQuery filterQuery) {
        PageResponse<JobPostResponse> jobPosts = jobPostService.findJobPosts(filterQuery, page, size);
        return new ResponseEntity<>(jobPosts, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<JobPostResponse>> searchJobs(@RequestParam(name = "searchQuery") String searchQuery,
                                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "20") int size) {
        PageResponse<JobPostResponse> jobPosts = jobPostService.searchJobPosts(searchQuery, page, size);
        return new ResponseEntity<>(jobPosts, HttpStatus.OK);
    }

    public ResponseEntity<PageResponse<JobPostResponse>>
    findJobPostsByRecruiterId(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "20") int size) {
        PageResponse<JobPostResponse> jobPosts = jobPostService.findJobPostsByRecruiterId(page, size);
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


    @PostMapping("{id}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long id) {
        jobPostService.toggleLikePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
