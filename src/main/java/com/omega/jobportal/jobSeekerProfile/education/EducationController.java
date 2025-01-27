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
@RequestMapping("api/v1/job-seeker/education")
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

    @GetMapping("/{id}")
    public ResponseEntity<List<EducationResponse>> viewEducationDetails(@PathVariable Long id) {
        List<EducationResponse> educationResponses = educationService.viewEducationDetails(id);
        return new ResponseEntity<>(educationResponses, HttpStatus.OK);
    }
}
