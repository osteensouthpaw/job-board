package com.omega.jobportal.user;


import com.omega.jobportal.user.data.UserRegistrationRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public AppUser(UserRegistrationRequest request, String encodedPassword) {
        this.firstName = request.firstName();
        this.lastName = request.lastName();
        this.password = encodedPassword;
        this.email = request.email();
        this.userType = request.userType();
        this.imageUrl = request.imageUrl();
        this.gender = request.gender();
        this.phone = request.phone();
    }

    public AppUser(OAuth2User oAuth2User) {
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String[] names = name.split(" ");
        if (names.length > 1) {
            this.firstName = names[0];
            this.lastName = names[1];
        } else this.firstName = name;
        this.email = email;
    }
}
