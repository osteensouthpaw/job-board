package com.omega.jobportal.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    @Query("""
   SELECT u FROM AppUser u WHERE u.email = :email
""")
    Optional<AppUser> findUserByEmail(String email);
}
