package com.omega.jobportal.user.verificationCode;

import com.omega.jobportal.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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

    public boolean verifyCode(String code) {
        return verificationCodeRepository.findByCode(code)
                .filter(verificationCode -> verificationCode.getCode().equals(code))
                .isPresent();
    }
}
