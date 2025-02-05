package com.omega.jobportal.user.verificationCode;

import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {
    private final VerificationCodeRepository verificationCodeRepository;

    private String generateVerificationCode() {
        return RandomStringUtils.secure().next(6);
    }

    public String createVerificationCode(AppUser appUser) {
        String code = generateVerificationCode();
        VerificationCode verificationCode = new VerificationCode(code, appUser);
        return verificationCodeRepository.save(verificationCode).getCode();
    }

    public void verifyCode(String code) {
        verificationCodeRepository.findByCode(code)
                .ifPresentOrElse(verificationCode -> verificationCodeRepository.deleteById(verificationCode.getId()),
                        () -> {
                            throw new ApiException("please activate your account to continue", HttpStatus.UNAUTHORIZED);
                        });
    }
}
