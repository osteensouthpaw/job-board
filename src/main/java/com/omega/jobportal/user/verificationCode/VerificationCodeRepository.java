package com.omega.jobportal.user.verificationCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, String> {
    @Query("""
            SELECT vc FROM VerificationCode vc WHERE vc.code = :code
            """)
    Optional<VerificationCode> findByCode(String code);
}
