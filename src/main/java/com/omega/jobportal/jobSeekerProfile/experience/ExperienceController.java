package com.omega.jobportal.jobSeekerProfile.experience;

import com.omega.jobportal.jobSeekerProfile.data.ExperienceRequest;
import com.omega.jobportal.jobSeekerProfile.data.ExperienceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/job-seeker/experiences")
public class ExperienceController {
    private final ExperienceService experienceService;

    @PostMapping
    public ResponseEntity<ExperienceResponse> createExperience(@RequestBody @Valid ExperienceRequest request) {
        ExperienceResponse experienceResponse = experienceService.saveExperience(request);
        return new ResponseEntity<>(experienceResponse, HttpStatus.CREATED);
    }
}
