package com.omega.jobportal.jobSeekerProfile.certification;

import com.omega.jobportal.jobSeekerProfile.data.CertificationRequest;
import com.omega.jobportal.jobSeekerProfile.data.CertificationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/job-seeker/{jobSeekerId}/certifications")
public class CertificationController {
    private final CertificationService certificationService;

    @GetMapping("{jobSeekerId}")
    public ResponseEntity<List<CertificationResponse>> findAllCertifications(@PathVariable Long jobSeekerId) {
        List<CertificationResponse> certifications = certificationService.findAllCertificationsByJobSeekerId(jobSeekerId);
        return new ResponseEntity<>(certifications, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CertificationResponse> findCertificationById(@PathVariable Long id) {
        CertificationResponse certification = certificationService.findCertificationById(id);
        return new ResponseEntity<>(certification, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CertificationResponse> updateCertification(@Valid @RequestBody CertificationRequest certificationRequest, @PathVariable Long id) {
        CertificationResponse certificationResponse = certificationService.updateCertificationById(certificationRequest, id);
        return new ResponseEntity<>(certificationResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CertificationResponse> createCertification(@RequestBody @Valid CertificationRequest request) {
        CertificationResponse certificationResponse = certificationService.addCertification(request);
        return new ResponseEntity<>(certificationResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCertificationById(@PathVariable Long id) {
        certificationService.deleteCertification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
