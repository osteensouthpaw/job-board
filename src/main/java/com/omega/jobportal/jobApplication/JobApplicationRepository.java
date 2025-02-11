package com.omega.jobportal.jobApplication;

import com.omega.jobportal.jobApplication.key.JobApplicationKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobApplicationRepository extends JpaRepository<JobApplication, JobApplicationKey> {

    @Query("""
            SELECT jb FROM JobPost jb JOIN JobApplication ja WHERE ja.id.jobPost.id = :id AND jb.recruiter.id = :recruiterId
            """)
    Page<JobApplication> findJobPostApplicationsById(Long id, Long recruiterId, Pageable pageable);
}
