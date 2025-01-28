package com.omega.jobportal.jobSeekerProfile.experience;

import com.omega.jobportal.jobSeekerProfile.data.ExperienceRequest;
import com.omega.jobportal.jobSeekerProfile.data.ExperienceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}")
    public ResponseEntity<ExperienceResponse> updateExperience(@RequestBody @Valid ExperienceRequest request, @PathVariable Long id) {
        ExperienceResponse experienceResponse = experienceService.updateExperience(request, id);
        return new ResponseEntity<>(experienceResponse, HttpStatus.OK);
    }
}
