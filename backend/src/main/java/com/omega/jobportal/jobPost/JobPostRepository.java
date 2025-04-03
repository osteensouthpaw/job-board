package com.omega.jobportal.jobPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface JobPostRepository extends JpaRepository<JobPost, Long>, JpaSpecificationExecutor<JobPost> {

    @Query(
            """
                     SELECT jb FROM JobPost jb WHERE jb.recruiter.id = :recruiterId
                    """
    )
    Page<JobPost> findJobPostsByRecruiterId(Long recruiterId, Pageable pageable);
}
