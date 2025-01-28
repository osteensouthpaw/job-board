package com.omega.jobportal.jobSeekerProfile.experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("""
            SELECT e FROM Experience e WHERE e.jobSeekerProfile.jobSeeker.id = :id
            """)
    List<Experience> findExperienceByJobSeekerId(Long id);
}
