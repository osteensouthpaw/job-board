package com.omega.jobportal.jobSeekerProfile.skills;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillSetRepository extends JpaRepository<SkillSet, Long> {
    @Query("SELECT s FROM SkillSet s WHERE s.jobSeekerProfile.jobSeeker.id = :jobSeekerId")
    List<SkillSet> findSkillSetsByJobSeekerId(Long jobSeekerId);
}
