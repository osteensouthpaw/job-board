package com.omega.jobportal.jobPost;


import com.omega.jobportal.company.Company;
import com.omega.jobportal.jobPost.data.JobPostRequest;
import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;
import com.omega.jobportal.location.Location;
import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Indexed
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
    @FullTextField
    private String jobTitle;

    @Column(length = 1000)
    @FullTextField
    private String description;

    @Column(nullable = false, name = "hourly_rate")
    private BigDecimal hourlyRate;

    @Column(name = "job_type")
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "work_mode")
    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @Column(name = "skill_level")
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "application_deadline")
    private LocalDateTime applicationDeadline;

    public JobPost(JobPostRequest request, AppUser recruiter, Company company, Location location) {
        this.recruiter = recruiter;
        this.company = company;
        this.location = location;
        this.jobTitle = request.jobTitle();
        this.description = request.jobDescription();
        this.hourlyRate = request.hourlyRate();
        this.jobType = request.jobType();
        this.workMode = request.workMode();
        this.experienceLevel = request.experienceLevel();
        this.applicationDeadline = request.applicationDeadline();
    }
}
