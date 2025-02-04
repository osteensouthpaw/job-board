package com.omega.jobportal.user.verificationCode;

import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_code")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String code;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public VerificationCode(String code, AppUser appUser) {
        this.code = code;
        this.appUser = appUser;
    }
}
