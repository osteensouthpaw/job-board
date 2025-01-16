package com.omega.jobportal.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false, length = 1000)
    private String bio;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
