package com.omega.jobportal.jobApplication;

import com.omega.jobportal.jobApplication.key.JobApplicationKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "job_applications")
public class JobApplication {
    @EmbeddedId
    private JobApplicationKey id;

    @Column(name = "application_date")
    @CreatedDate
    private LocalDateTime applicationDate;

    public JobApplication(JobApplicationKey id) {
        this.id = id;
    }
}
