package com.omega.jobportal.jobApplication;

import com.omega.jobportal.jobApplication.key.JobApplicationKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, JobApplicationKey> {

    @Query("""
             SELECT ja FROM JobApplication ja WHERE ja.applicant.id = :applicantId
            """)
    Page<JobApplication> findJobApplicationsByAppUserId(Long applicantId, Pageable pageable);

    @Query("""
                SELECT ja FROM JobApplication ja WHERE ja.jobPost.id = :jobPostId AND ja.jobPost.recruiter.id = :recruiterId
            """)
    Page<JobApplication> findByJobPostIdAndRecruiterId(Long jobPostId, Long recruiterId, Pageable pageable);

    @Query("""
            SELECT ja FROM JobApplication ja WHERE ja.applicant.id = :applicantId AND ja.jobPost.id = :jobPostId
            """)
    Optional<JobApplication> findJobApplicationByJobPostIdAndApplicantId(Long jobPostId, Long applicantId);
}
