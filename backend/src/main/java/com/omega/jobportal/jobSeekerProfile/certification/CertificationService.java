package com.omega.jobportal.jobSeekerProfile.certification;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfileService;
import com.omega.jobportal.jobSeekerProfile.data.CertificationRequest;
import com.omega.jobportal.jobSeekerProfile.data.CertificationResponse;
import com.omega.jobportal.jobSeekerProfile.dtoMapper.CertificationDtoMapper;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final AuthenticationService authenticationService;
    private final CertificationRepository certificationRepository;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final CertificationDtoMapper certificationDtoMapper;

    public CertificationResponse addCertification(CertificationRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile profile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        Certification certification = new Certification(profile, request);
        return certificationDtoMapper.apply(certificationRepository.save(certification));
    }

    public CertificationResponse findCertificationById(Long id) {
        return certificationRepository.findById(id)
                .map(certificationDtoMapper)
                .orElseThrow(() -> new ApiException("Certification with id " + id + " not found", HttpStatus.NOT_FOUND));
    }

    public void deleteCertification(Long id) {
        certificationRepository.findById(id).ifPresentOrElse(certification -> {
                    if (!isCertificateOwner(certification))
                        throw new ApiException("you are not education owner", HttpStatus.FORBIDDEN);
                    certificationRepository.deleteById(certification.getId());
                },
                () -> {
                    throw new ApiException("Certification with id " + id + " not found", HttpStatus.NOT_FOUND);
                });
    }

    public CertificationResponse updateCertificationById(CertificationRequest request, Long certificationId) {
        return certificationRepository.findById(certificationId)
                .map(certification -> {
                    if (!isCertificateOwner(certification))
                        throw new ApiException("you are not education owner", HttpStatus.FORBIDDEN);
                    Certification updatedCertification = certificationDtoMapper.apply(request, certification);
                    Certification savedCertification = certificationRepository.save(updatedCertification);
                    return certificationDtoMapper.apply(savedCertification);
                })
                .orElseThrow(() -> new ApiException("Certification not found", HttpStatus.NOT_FOUND));

    }

    private boolean isCertificateOwner(Certification certification) {
        AppUser loggedInUser = authenticationService.getSession();
        return Objects.equals(
                loggedInUser.getId(),
                certification.getJobSeekerProfile().getJobSeeker().getId()
        );
    }

    public List<CertificationResponse> findAllCertificationsByJobSeekerId() {
        AppUser loggedInUser = authenticationService.getSession();
        JobSeekerProfile profile = jobSeekerProfileService.findJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        return certificationRepository.findByJobSeekerProfileId(profile.getId())
                .stream().map(certificationDtoMapper).toList();
    }

}
