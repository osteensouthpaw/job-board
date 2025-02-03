package com.omega.jobportal.jobPost;

import com.omega.jobportal.jobApplication.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    @Query("""
                    SELECT jb FROM JobPost jb JOIN JobApplication ja WHERE ja.id.jobPost.id = :id AND jb.recruiter.id = :recruiterId
            """)
    List<JobApplication> findJobPostApplicationsById(Long id, Long recruiterId);
}
