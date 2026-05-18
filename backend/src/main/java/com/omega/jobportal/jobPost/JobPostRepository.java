package com.omega.jobportal.jobPost;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public interface JobPostRepository extends JpaRepository<JobPost, Long>, JpaSpecificationExecutor<JobPost> {

    @Query("SELECT jb FROM JobPost jb WHERE jb.recruiter.id = :recruiterId")
    Page<JobPost> findJobPostsByRecruiterId(Long recruiterId, Pageable pageable);

    @Query("SELECT jp FROM JobPost jp JOIN jp.likedBy u WHERE u.id = :userId")
    Page<JobPost> findLikedJobPostsByUserId(Long userId, Pageable pageable);

    @Query("SELECT jb FROM JobPost jb WHERE jb.organization.id = :organizationId")
    Page<JobPost> findJobPostsByOrganizationId(Long organizationId, Pageable pageable);

    @Query("SELECT count(jp) FROM JobPost jp WHERE jp.applicationDeadline > :now")
    int findTotalOpenJobPosts(LocalDateTime now);

    @EntityGraph(attributePaths = {"recruiter", "organization", "likedBy", "jobApplications"})
    Page<JobPost> findAll(@Nullable Specification<JobPost> spec, @NonNull Pageable pageable);

}
