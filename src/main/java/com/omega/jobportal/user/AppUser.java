package com.omega.jobportal.user;


import com.omega.jobportal.user.data.UserRegistrationRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public AppUser(UserRegistrationRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.firstName = request.firstName();
        this.lastName = request.lastName();
        //TODO: get bean from spring context to encode this password
        this.password = encoder.encode(request.password());
        this.email = request.email();
        this.userType = request.userType();
        this.imageUrl = request.imageUrl();
        this.gender = request.gender();
        this.phone = request.phone();
    }
}
