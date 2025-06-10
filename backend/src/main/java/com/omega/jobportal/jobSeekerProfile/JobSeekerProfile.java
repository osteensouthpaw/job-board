package com.omega.jobportal.jobSeekerProfile;

import com.omega.jobportal.jobPost.enumerations.ExperienceLevel;
import com.omega.jobportal.jobSeekerProfile.data.JobSeekerProfileRequest;
import com.omega.jobportal.jobSeekerProfile.education.Education;
import com.omega.jobportal.jobSeekerProfile.experience.Experience;
import com.omega.jobportal.jobSeekerProfile.skills.SkillSet;
import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Indexed
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_seeker_profiles")
public class JobSeekerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "job_seeker_id", unique = true)
    private AppUser jobSeeker;

    @Column(name = "current_annual_salary")
    private BigDecimal currentAnnualSalary;

    @Column(name = "phone")
    private String phone;

    @Column(length = 1000)
    @FullTextField
    private String bio;

    @Column(nullable = false)
    private String profession;

    @Column(name = "personal_website_url")
    private String personalWebsiteUrl;

    @Column(name = "linkedin_url")
    private String linkedInUrl;

    @Column(name = "github_url")
    private String gitHubUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "experience_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @OneToMany(
            mappedBy = "jobSeekerProfile",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(
            mappedBy = "jobSeekerProfile",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<SkillSet> skillSets = new ArrayList<>();

    @OneToMany(
            mappedBy = "jobSeekerProfile",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Education> educations = new ArrayList<>();


    public JobSeekerProfile(JobSeekerProfileRequest request, AppUser jobSeeker) {
        this.jobSeeker = jobSeeker;
        this.bio = request.bio();
        this.profession = request.profession();
        this.personalWebsiteUrl = request.personalWebsiteUrl();
        this.linkedInUrl = request.linkedInUrl();
        this.gitHubUrl = request.gitHubUrl();
        this.twitterUrl = request.twitterUrl();
        this.experienceLevel = request.experienceLevel();
        this.currentAnnualSalary = request.currentAnnualSalary();
        this.dateOfBirth = request.dateOfBirth();
    }
}
