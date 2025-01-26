package com.omega.jobportal.jobSeekerProfile.skills;

import com.omega.jobportal.jobSeekerProfile.JobSeekerProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "skills")
@Getter
@Setter
public class SkillSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile_id")
    private JobSeekerProfile jobSeekerProfile;

    @Column(nullable = false)
    private String skill;

    @Column(length = 500)
    private String description;
}
