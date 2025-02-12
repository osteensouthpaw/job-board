package com.omega.jobportal.user;

import com.omega.jobportal.config.SecurityUser;
import com.omega.jobportal.email.EmailService;
import com.omega.jobportal.email.EmailUtils;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.user.data.UpdateUserPasswordRequest;
import com.omega.jobportal.user.data.UserRegistrationRequest;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import com.omega.jobportal.user.passwordReset.PasswordResetToken;
import com.omega.jobportal.user.passwordReset.PasswordResetTokenRepository;
import com.omega.jobportal.user.verificationCode.VerificationCodeService;
import com.omega.jobportal.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Transactional
    public UserResponse createUser(UserRegistrationRequest request) {
        boolean existsByEmail = userRepository.existsByEmail(request.email());
        if (existsByEmail)
            throw new BadCredentialsException("email already exists");

        AppUser appUser = new AppUser(request, passwordEncoder.encode(request.password()));
        AppUser user = userRepository.save(appUser);
        sendVerificationEmail(user);
        return userDtoMapper.apply(user);
    }

    private void sendVerificationEmail(AppUser user) {
        String verificationCode = verificationCodeService.createVerificationCode(user);
        String message = EmailUtils.getAccountVerificationEmailMessage(user.getFirstName(), verificationCode);
        emailService.sendSimpleMailMessage(user.getEmail(), message);
    }

    public PageResponse<UserResponse> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserResponse> pagedUsers = userRepository.findAll(pageRequest).map(userDtoMapper);
        return new PageResponse<>(pagedUsers);
    }

    public AppUser findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void forgotPassword(String email) {
        AppUser user = findUserByEmail(email);
        var passwordResetToken = new PasswordResetToken(user);
        var resetToken = passwordResetTokenRepository.save(passwordResetToken);
        String message = EmailUtils.getResetPasswordEmailMessage(user.getFirstName(), resetToken.getToken());
        emailService.sendSimpleMailMessage(user.getEmail(), message);
    }

    @Transactional
    public void resetPassword(UpdateUserPasswordRequest request) {
        if (!request.password().equals(request.confirmPassword()))
            throw new ApiException("Passwords do not match!", HttpStatus.BAD_REQUEST);

        passwordResetTokenRepository.findByToken(request.passwordToken())
                .ifPresentOrElse(token -> {
                            AppUser appUser = token.getAppUser();
                            appUser.setPassword(passwordEncoder.encode(request.password()));
                            userRepository.save(appUser);
                            //todo: send confirmation email to user;
                        },
                        () -> {
                            throw new ApiException("password reset token not found", HttpStatus.NOT_FOUND);
                        });
    }

    public AppUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ApiException("user with email".concat(email), HttpStatus.NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
