package com.omega.jobportal.jobApplication;

import com.omega.jobportal.jobApplication.data.JobApplicationRequest;
import com.omega.jobportal.jobApplication.data.JobApplicationResponse;
import com.omega.jobportal.utils.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/job-applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    //all job applications of the current logged-in user
    @GetMapping
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<PageResponse<JobApplicationResponse>>
    jobApplicationsByApplicant(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        var applications = jobApplicationService.findAllApplicationsByApplicant(page, size);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{jobPostId}/applicants/{applicantId}")
    public ResponseEntity<JobApplicationResponse>
    findJobApplicationByJobPostIdAndApplicantId(@PathVariable Long jobPostId, @PathVariable Long applicantId) {
        JobApplicationResponse jobApplication = jobApplicationService.findJobApplicationByJobPostIdAndApplicantId(jobPostId, applicantId);
        return ResponseEntity.ok(jobApplication);
    }

    @PostMapping
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<JobApplicationResponse> createJobApplication(@RequestBody @Valid JobApplicationRequest request) {
        JobApplicationResponse jobApplication = jobApplicationService.createJobApplication(request);
        return new ResponseEntity<>(jobApplication, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable Long id) {
        jobApplicationService.deleteJobApplication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping("/accept")
    public ResponseEntity<Void> acceptApplication(@RequestParam Long applicantId, @RequestParam Long jobPostId) {
        jobApplicationService.acceptApplication(applicantId, jobPostId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/reject")
    public ResponseEntity<Void> rejectApplication(@RequestParam Long applicantId, @RequestParam Long jobPostId) {
        jobApplicationService.rejectApplication(applicantId, jobPostId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
