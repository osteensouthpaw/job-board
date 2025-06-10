package com.omega.jobportal.jobSeekerProfile;

import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileRequest;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileResponse;
import com.omega.jobportal.utils.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/job-seekers")
@RequiredArgsConstructor
public class JobSeekerProfileController {
    private final JobSeekerProfileService jobSeekerProfileService;

    @PostMapping
    public ResponseEntity<JobSeekerProfileResponse> createJobSeekerProfile(@RequestBody @Valid JobSeekerProfileRequest request) {
        var profile = jobSeekerProfileService.createProfile(request);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponse<JobSeekerProfileResponse>>
    searchProfileBios(@RequestParam(name = "searchQuery") String searchQuery,
                      @RequestParam(value = "page", defaultValue = "0") int page,
                      @RequestParam(value = "size", defaultValue = "10") int size) {
        PageResponse<JobSeekerProfileResponse> jobSeekers = jobSeekerProfileService.searchJobSeekers(searchQuery, page, size);
        return new ResponseEntity<>(jobSeekers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSeekerProfileResponse> viewProfile(@PathVariable Long id) {
        var jobSeekerProfileResponse = jobSeekerProfileService.viewJobSeekerProfile(id);
        return new ResponseEntity<>(jobSeekerProfileResponse, HttpStatus.OK);
    }

    @PatchMapping("/me")
    public ResponseEntity<JobSeekerProfileResponse> updateProfile(@RequestBody @Valid JobSeekerProfileRequest request) {
        var jobSeekerProfileResponse = jobSeekerProfileService.updateJobSeekerProfile(request);
        return new ResponseEntity<>(jobSeekerProfileResponse, HttpStatus.OK);
    }
}
