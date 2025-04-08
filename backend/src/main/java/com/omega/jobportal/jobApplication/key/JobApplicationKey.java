package com.omega.jobportal.jobApplication.key;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationKey implements Serializable {
    private Long applicantId;
    private Long jobPostId;
}
