package com.omega.jobportal.jobApplication;

import com.omega.jobportal.jobApplication.key.JobApplicationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, JobApplicationKey> {

    @Query("""
            SELECT jb FROM JobPost jb JOIN JobApplication ja WHERE ja.id.jobPost.id = :id AND jb.recruiter.id = :recruiterId
            """)
    List<JobApplication> findJobPostApplicationsById(Long id, Long recruiterId);
}
