package com.omega.jobportal.jobApplication;

import com.omega.jobportal.jobApplication.data.JobApplicationRequest;
import com.omega.jobportal.jobApplication.data.JobApplicationResponse;
import com.omega.jobportal.utils.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/job-applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

//    @GetMapping
//    public ResponseEntity<PageResponse<JobApplicationResponse>>
//    getAllJobApplications(@RequestParam(value = "page", defaultValue = "0") int page,
//                          @RequestParam(value = "size", defaultValue = "10") int size) {
//        var jobApplications = jobApplicationService.findAllApplications(page, size);
//        return new ResponseEntity<>(jobApplications, HttpStatus.OK);
//    }

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
    public ResponseEntity<PageResponse<JobApplicationResponse>>
    jobApplicationsByPostId(@PathVariable Long id,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size) {
        var applications = jobApplicationService.findJobPostApplicationsByJobPostId(id, page, size);
        return ResponseEntity.ok(applications);
    }

    @GetMapping
    public ResponseEntity<PageResponse<JobApplicationResponse>>
    jobApplicationsByApplicant(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        var applications = jobApplicationService.findAllApplicationsByApplicant(page, size);
        return ResponseEntity.ok(applications);
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
