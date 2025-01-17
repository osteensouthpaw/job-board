package com.omega.jobportal.jobSeekerProfile;

import com.omega.jobportal.jobPost.ExperienceLevel;
import com.omega.jobportal.jobSeekerProfile.skills.SkillSet;
import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

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

    @Column(length = 1000)
    private String bio;

    private String profession;

    @Column(name = "personal_website")
    private String personalWebsite;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "experience_level")
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @ManyToMany
    @JoinTable(
            name = "job_seeker_skills",
            joinColumns = @JoinColumn(name = "job_seeker_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<SkillSet> skills;
}
