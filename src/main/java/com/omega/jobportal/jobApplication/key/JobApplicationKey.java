package com.omega.jobportal.jobApplication.key;


import com.omega.jobportal.jobPost.JobPost;
import com.omega.jobportal.user.AppUser;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class JobApplicationKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private AppUser applicant;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;
}
