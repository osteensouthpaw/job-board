package com.omega.jobportal.user.passwordReset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    @Query("""
                  SELECT p FROM PasswordResetToken p WHERE p.token = :passwordToken
            """)
    Optional<PasswordResetToken> findByToken(String passwordToken);
}
