package com.omega.jobportal.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(
            """
                    SELECT o FROM Organization o WHERE o.recruiter.id = :recruiterId"""

    )
    Optional<Organization> findOrganizationByRecruiterId(Long recruiterId);
}
