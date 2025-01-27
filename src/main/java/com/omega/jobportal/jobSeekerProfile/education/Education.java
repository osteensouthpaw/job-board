package com.omega.jobportal.jobSeekerProfile.education;

import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import com.omega.jobportal.jobSeekerProfile.data.EducationRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "education_details")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile_id")
    private JobSeekerProfile jobSeekerProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level")
    private EducationLevel educationLevel;

    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @Column(nullable = false)
    private String institution;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "certificate_url")
    private String certificateUrl;

    public Education(EducationRequest request, JobSeekerProfile jobSeekerProfile) {
        this.jobSeekerProfile = jobSeekerProfile;
        this.educationLevel = request.educationLevel();
        this.fieldOfStudy = request.fieldOfStudy();
        this.institution = request.institution();
        this.startDate = request.startDate();
        this.endDate = request.endDate();
        this.certificateUrl = request.certificateUrl();
    }
}
