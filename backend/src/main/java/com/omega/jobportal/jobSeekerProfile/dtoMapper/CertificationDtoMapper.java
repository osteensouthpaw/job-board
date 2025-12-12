package com.omega.jobportal.jobSeekerProfile.dtoMapper;

import com.omega.jobportal.jobSeekerProfile.certification.Certification;
import com.omega.jobportal.jobSeekerProfile.data.CertificationRequest;
import com.omega.jobportal.jobSeekerProfile.data.CertificationResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CertificationDtoMapper implements Function<Certification, CertificationResponse> {
    @Override
    public CertificationResponse apply(Certification certification) {
        return new CertificationResponse(
                certification.getId(),
                certification.getJobSeekerProfile().getId(),
                certification.getName(),
                certification.getIssuer(),
                certification.getIssuedOn(),
                certification.getCredentialId(),
                certification.getCertificateUrl()
        );
    }

    public Certification apply(CertificationRequest certificationRequest, Certification certification) {
        certification.setName(certificationRequest.name());
        certification.setIssuer(certificationRequest.issuer());
        certification.setIssuedOn(certificationRequest.issuedOn());
        certification.setCredentialId(certificationRequest.credentialId());
        certification.setCertificateUrl(certificationRequest.certificateUrl());
        return certification;
    }
}

