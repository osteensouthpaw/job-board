package com.omega.jobportal.jobApplication;

import com.omega.jobportal.jobApplication.key.JobApplicationKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "job_applications")
public class JobApplication {
    @EmbeddedId
    private JobApplicationKey id;

    @Column(name = "application_date")
    @CreationTimestamp
    private LocalDateTime applicationDate;
}
