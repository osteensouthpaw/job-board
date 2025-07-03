package com.omega.jobportal.jobSeekerProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Long> {

    @Query("SELECT js FROM JobSeekerProfile js WHERE js.jobSeeker.id = :jobSeekerId")
    Optional<JobSeekerProfile> findJobSeekerProfileByJobSeekerId(Long jobSeekerId);

    @Modifying
    @Query("DELETE FROM JobSeekerProfile js WHERE js.jobSeeker.id = :jobSeekerId")
    void deleteJobSeekerProfileByJobSeekerId(Long jobSeekerId);
}
