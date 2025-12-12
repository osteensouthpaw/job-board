package com.omega.jobportal.jobSeekerProfile.certification;

import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.data.CertificationRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "certifications")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile_id")
    private JobSeekerProfile jobSeekerProfile;

    private String name;
    private String issuer;

    @Column(name = "credential_id")
    private String credentialId;

    @Column(name = "certificate_url")
    private String certificateUrl;

    @Column(name = "issued_on")
    private LocalDate issuedOn;

    public Certification(JobSeekerProfile jobSeekerProfile, CertificationRequest request) {
        this.jobSeekerProfile = jobSeekerProfile;
        this.name = request.name();
        this.issuer = request.issuer();
        this.credentialId = request.credentialId();
        this.certificateUrl = request.certificateUrl();
        this.issuedOn = request.issuedOn();
    }
}
