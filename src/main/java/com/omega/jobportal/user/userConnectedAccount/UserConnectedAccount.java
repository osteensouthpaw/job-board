package com.omega.jobportal.user.userConnectedAccount;

import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Table(name = "user_connected_accounts")
@NoArgsConstructor
public class UserConnectedAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "provider_name")
    private String providerName;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @CreatedDate
    @Column(name = "connected_date", updatable = false)
    private LocalDateTime connectedDate;

    public UserConnectedAccount(String providerId, String providerName, AppUser appUser) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.appUser = appUser;
    }
}
