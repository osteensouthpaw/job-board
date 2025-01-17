package com.omega.jobportal.jobSeekerProfile.experience;


import com.omega.jobportal.jobPost.JobType;
import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id")
    private AppUser jobSeeker;

    @Column(name = "is_current_job")
    private boolean isCurrentJob;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "job_type")
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_location")
    private String jobLocation;

    @Column(length = 1000)
    private String description;
}
