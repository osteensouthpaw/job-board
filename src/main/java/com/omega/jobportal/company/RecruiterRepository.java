package com.omega.jobportal.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepository extends JpaRepository<Company, Long> {
}
