package com.omega.jobportal.recruiter;

import com.omega.jobportal.jobApplication.JobApplicationService;
import com.omega.jobportal.jobApplication.data.JobApplicationResponse;
import com.omega.jobportal.jobPost.JobPostService;
import com.omega.jobportal.jobPost.data.JobPostResponse;
import com.omega.jobportal.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/recruiters")
@RequiredArgsConstructor
public class RecruiterController {
    private final JobPostService jobPostService;
    private final JobApplicationService jobApplicationService;

    @GetMapping("/job-posts")
    public ResponseEntity<PageResponse<JobPostResponse>>
    findJobPostsByRecruiterId(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy) {
        PageResponse<JobPostResponse> jobPosts = jobPostService.findJobPostsByRecruiterId(page, size, sortBy);
        return new ResponseEntity<>(jobPosts, HttpStatus.OK);
    }

    @GetMapping("/job-applications")
    public ResponseEntity<PageResponse<JobApplicationResponse>>
    findAllApplicationsForRecruiter(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "applicationDate") String sortBy) {
        PageResponse<JobApplicationResponse> allApplications = jobApplicationService.findAllApplicationsForRecruiter(page, size, sortBy);
        return new ResponseEntity<>(allApplications, HttpStatus.OK);
    }
}
