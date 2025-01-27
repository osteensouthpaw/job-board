package com.omega.jobportal.jobSeekerProfile;

import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileRequest;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/job-seeker")
@RequiredArgsConstructor
public class JobSeekerProfileController {
    private final JobSeekerProfileService jobSeekerProfileService;

    @PostMapping
    public ResponseEntity<JobSeekerProfileResponse> createJobSeekerProfile(@RequestBody @Valid JobSeekerProfileRequest request) {
        var profile = jobSeekerProfileService.createProfile(request);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<JobSeekerProfileResponse> viewProfile() {
        var jobSeekerProfileResponse = jobSeekerProfileService.viewJobSeekerProfile();
        return new ResponseEntity<>(jobSeekerProfileResponse, HttpStatus.OK);
    }
}
