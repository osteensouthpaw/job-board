package com.omega.jobportal.jobApplication;

import com.omega.jobportal.jobApplication.data.JobApplicationRequest;
import com.omega.jobportal.jobApplication.data.JobApplicationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/job-applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    private final RecruiterApplicationService recruiterApplicationService;

    @GetMapping
    public ResponseEntity<List<JobApplicationResponse>> getAllJobApplications() {
        List<JobApplicationResponse> allApplications = jobApplicationService.findAllApplications();
        return new ResponseEntity<>(allApplications, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponse> createJobApplication(@RequestBody @Valid JobApplicationRequest request) {
        JobApplicationResponse jobApplication = jobApplicationService.createJobApplication(request);
        return new ResponseEntity<>(jobApplication, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable Long id) {
        jobApplicationService.deleteJobApplication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<JobApplicationResponse>> jobApplicationsByPostId(@PathVariable Long id) {
        var jobPostApplicationsByJobPostId = recruiterApplicationService.findJobPostApplicationsByJobPostId(id);
        return ResponseEntity.ok(jobPostApplicationsByJobPostId);
    }

    @PatchMapping("/accept")
    public ResponseEntity<Void> acceptApplication(@RequestParam Long applicantId, @RequestParam Long jobPostId) {
        recruiterApplicationService.acceptApplication(applicantId, jobPostId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
