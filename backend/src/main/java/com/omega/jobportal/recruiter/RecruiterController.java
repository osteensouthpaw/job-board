package com.omega.jobportal.recruiter;

import com.omega.jobportal.jobApplication.JobApplicationService;
import com.omega.jobportal.jobApplication.data.JobApplicationResponse;
import com.omega.jobportal.jobPost.JobPostService;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/recruiters")
@RequiredArgsConstructor
public class RecruiterController {
    private final JobPostService jobPostService;
    private final JobApplicationService jobApplicationService;

    @GetMapping("/job-posts")
    public ResponseEntity<PageResponse<JobPostResponse>>
    findJobPostsByRecruiterId(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "20") int size) {
        PageResponse<JobPostResponse> jobPosts = jobPostService.findJobPostsByRecruiterId(page, size);
        return new ResponseEntity<>(jobPosts, HttpStatus.OK);
    }

    //all job applications of a particular job post
    @GetMapping("job-posts/{id}/applications")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<PageResponse<JobApplicationResponse>>
    jobApplicationsByPostId(@PathVariable Long id,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size) {
        var applications = jobApplicationService.findJobPostApplicationsByJobPostId(id, page, size);
        return ResponseEntity.ok(applications);
    }

}
