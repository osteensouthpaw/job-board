package com.omega.jobportal.jobPost;


import com.omega.jobportal.company.Company;
import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;
import com.omega.jobportal.location.Location;
import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "job_posts")
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private AppUser recruiter;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(length = 1000)
    private String description;

    @Column(name = "job_type")
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "work_mode")
    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @Column(name = "skill_level")
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
