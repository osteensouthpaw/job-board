package com.omega.jobportal.jobSeekerProfile.education;

import com.omega.jobportal.jobSeekerProfile.data.EducationRequest;
import com.omega.jobportal.jobSeekerProfile.data.EducationResponse;
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
@RequestMapping("api/v1/job-seeker/education")
public class EducationController {
    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponse> saveEducation(@RequestBody @Valid EducationRequest request) {
        EducationResponse education = educationService.createEducation(request);
        return new ResponseEntity<>(education, HttpStatus.CREATED);
    }
}
