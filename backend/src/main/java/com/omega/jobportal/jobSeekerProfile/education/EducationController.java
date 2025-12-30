package com.omega.jobportal.jobSeekerProfile.education;

import com.omega.jobportal.jobSeekerProfile.data.EducationRequest;
import com.omega.jobportal.jobSeekerProfile.data.EducationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/job-seekers/{jobSeekerId}/education")
public class EducationController {
    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponse> saveEducation(@RequestBody @Valid EducationRequest request) {
        EducationResponse education = educationService.createEducation(request);
        return new ResponseEntity<>(education, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> viewEducationDetails(@PathVariable Long jobSeekerId) {
        List<EducationResponse> educationResponses = educationService.findEducationsByJobSeekerId(jobSeekerId);
        return new ResponseEntity<>(educationResponses, HttpStatus.OK);
    }

    @GetMapping("{educationId}")
    public ResponseEntity<EducationResponse> findEducationById(@PathVariable Long jobSeekerId, @PathVariable Long educationId) {
        EducationResponse educationResponse = educationService.findEducationById(jobSeekerId, educationId);
        return new ResponseEntity<>(educationResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EducationResponse> updateEducation(@RequestBody @Valid EducationRequest request, @PathVariable Long id) {
        EducationResponse educationResponse = educationService.editEducationDetail(request, id);
        return new ResponseEntity<>(educationResponse, HttpStatus.OK);
    }
}
