package com.omega.jobportal.jobApplication;

import com.omega.jobportal.jobApplication.key.JobApplicationKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, JobApplicationKey> {

}
