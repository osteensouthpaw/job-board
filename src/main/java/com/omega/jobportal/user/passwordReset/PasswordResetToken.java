package com.omega.jobportal.user.passwordReset;

import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "password_reset_token")
@Getter
@Setter
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @Column(name = "is_email_sent")
    private boolean isEmailSent = false;
    private LocalDateTime expiresAt;

    @ManyToOne
    private AppUser appUser;

    public PasswordResetToken(AppUser user) {
        appUser = user;
        this.token = RandomStringUtils.random(6, false, true);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public void onEmailSent() {
        this.isEmailSent = true;
        this.expiresAt = LocalDateTime.now().plusMinutes(15);
    }
}
