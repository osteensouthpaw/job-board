package com.omega.jobportal.jobSeekerProfile.education;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {

    @Query("""
            SELECT e FROM Education e WHERE e.jobSeekerProfile.jobSeeker.id = :id
            """)
    List<Education> findEducationByJobSeekerId(Long id);
}
