package com.omega.jobportal.user;

import com.omega.jobportal.auth.AuthenticationService;
import com.omega.jobportal.email.EmailService;
import com.omega.jobportal.email.EmailUtils;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.jobSeekerProfile.JobSeekerProfileService;
import com.omega.jobportal.user.data.*;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import com.omega.jobportal.user.passwordReset.PasswordResetToken;
import com.omega.jobportal.user.passwordReset.PasswordResetTokenRepository;
import com.omega.jobportal.user.userConnectedAccount.UserConnectedAccount;
import com.omega.jobportal.user.userConnectedAccount.UserConnectedAccountRepository;
import com.omega.jobportal.user.verificationCode.VerificationCodeService;
import com.omega.jobportal.utils.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.omega.jobportal.constants.Constants.NEW_USER_ACCOUNT_VERIFICATION;
import static com.omega.jobportal.constants.Constants.RESET_PASSWORD;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final AuthenticationService authenticationService;
    private final UserConnectedAccountRepository userConnectedAccountRepository;
    private final JobSeekerProfileService jobSeekerProfileService;

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

    public UserResponse completeUserRegistration(CompleteRegistrationRequest request) {
        return userRepository.findById(request.userId())
                .map(user -> {
                    user.setUserType(request.userType());
                    user.setImageUrl(request.imageUrl());
                    user.setGender(request.gender());
                    return userDtoMapper.apply(userRepository.save(user));
                })
                .orElseThrow(() -> new ApiException("User not found, please create account first to continue", HttpStatus.NOT_FOUND));
    }

    public UserResponse updateUser(UserUpdateRequest request) {
        AppUser loggedInUser = authenticationService.getSession();
        return userRepository.findById(loggedInUser.getId())
                .map(user -> {
                    user.setFirstName(request.firstName());
                    user.setLastName(request.lastName());
                    user.setImageUrl(request.imageUrl());
                    return userDtoMapper.apply(userRepository.save(user));
                }).orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }

    private void sendVerificationEmail(AppUser user) {
        String verificationCode = verificationCodeService.createVerificationCode(user);
        String message = EmailUtils.getAccountVerificationEmailMessage(user.getFirstName(), verificationCode);
        emailService.sendSimpleMailMessage(user.getEmail(), message, NEW_USER_ACCOUNT_VERIFICATION);
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
        emailService.sendSimpleMailMessage(user.getEmail(), message, RESET_PASSWORD);
    }

    @Transactional
    public void resetPassword(UpdateUserPasswordRequest request) {
        isPasswordMatch(request.password(), request.confirmPassword());
        passwordResetTokenRepository.findByToken(request.passwordToken())
                .ifPresentOrElse(token -> {
                            if (token.isExpired())
                                throw new ApiException("Password reset link is expired", HttpStatus.BAD_REQUEST);
                            AppUser appUser = token.getAppUser();
                            appUser.setPassword(passwordEncoder.encode(request.password()));
                            userRepository.save(appUser);
                            //todo: send confirmation email to user;
                        },
                        () -> {
                            throw new ApiException("password reset token not found", HttpStatus.NOT_FOUND);
                        });
    }

    public void updatePassword(UpdateUserPasswordRequest request) {
        AppUser appUser = authenticationService.getSession();
        isPasswordMatch(request.password(), request.confirmPassword());
        boolean isValidPassword = passwordEncoder.matches(request.oldPassword(), appUser.getPassword());
        if (!isValidPassword)
            throw new ApiException("Wrong password", HttpStatus.BAD_REQUEST);

        if (request.oldPassword().equals(request.password()))
            throw new ApiException("old password cannot be new password", HttpStatus.BAD_REQUEST);

        String hashedPassword = passwordEncoder.encode(request.password());
        appUser.setPassword(hashedPassword);
        userRepository.save(appUser);
    }

    public AppUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ApiException("user with email".concat(email), HttpStatus.NOT_FOUND));
    }

    private void isPasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword))
            throw new ApiException("Passwords do not match!", HttpStatus.BAD_REQUEST);
    }

    public List<UserConnectedAccount> findUserConnectedAccounts() {
        AppUser loggedInUser = authenticationService.getSession();
        return userConnectedAccountRepository.findByAppUserId(loggedInUser.getId());
    }

    public void deleteUserAccount(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Authentication authentication) {
        AppUser loggedInUser = authenticationService.getSession();
        jobSeekerProfileService.deleteJobSeekerProfileByJobSeekerId(loggedInUser.getId());
        userRepository.deleteById(loggedInUser.getId());
        authenticationService.logout(request, response, authentication);
    }
}