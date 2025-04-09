package com.omega.jobportal.jobPost;


import com.omega.jobportal.company.Organization;
import com.omega.jobportal.jobApplication.JobApplication;
import com.omega.jobportal.jobPost.data.JobPostRequest;
import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobPost.enumerations.JobType;
import com.omega.jobportal.jobPost.enumerations.WorkMode;
import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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
    private Organization organization;

    private String location;

    @Column(name = "job_title")
    @FullTextField
    private String jobTitle;

    @Column(length = 1000)
    @FullTextField
    private String description;

    @Column(nullable = false, name = "salary")
    private BigDecimal salary;

    @Column(name = "job_type")
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "work_mode")
    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @Column(name = "skill_level")
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @OneToMany(
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
            mappedBy = "jobPost"
    )
    private List<JobApplication> jobApplications = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "job_post_likes",
            joinColumns = @JoinColumn(name = "job_post_id"),
            inverseJoinColumns = @JoinColumn(name = "app_user_id")
    )
    private Set<AppUser> likedBy = new HashSet<>();

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "application_deadline")
    private LocalDateTime applicationDeadline;

    public JobPost(JobPostRequest request, AppUser recruiter, Organization organization) {
        this.recruiter = recruiter;
        this.organization = organization;
        this.location = request.location();
        this.jobTitle = request.jobTitle();
        this.description = request.jobDescription();
        this.salary = request.salary();
        this.jobType = request.jobType();
        this.workMode = request.workMode();
        this.experienceLevel = request.experienceLevel();
        this.applicationDeadline = request.applicationDeadline();
    }
}
