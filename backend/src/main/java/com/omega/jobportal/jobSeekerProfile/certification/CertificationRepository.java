package com.omega.jobportal.jobSeekerProfile.certification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    @Query("SELECT c FROM Certification c WHERE c.jobSeekerProfile.id = :profileId")
    List<Certification> findByJobSeekerProfileId(Long profileId);
}
