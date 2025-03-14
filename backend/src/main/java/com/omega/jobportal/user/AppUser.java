package com.omega.jobportal.user;


import com.omega.jobportal.user.data.UserRegistrationRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public AppUser(UserRegistrationRequest request, String encodedPassword) {
        separateNames(request.name());
        this.password = encodedPassword;
        this.email = request.email();
        this.userType = UserType.PENDING;
    }

    public AppUser(String name, String email) {
        separateNames(name);
        this.email = email;
    }

    private void separateNames(String name) {
        String[] names = name.split(" ");
        if (names.length > 1) {
            this.firstName = names[0];
            this.lastName = names[1];
        } else this.firstName = name;
    }
}
