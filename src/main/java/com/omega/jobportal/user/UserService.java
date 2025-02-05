package com.omega.jobportal.user;

import com.omega.jobportal.config.SecurityUser;
import com.omega.jobportal.email.EmailService;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.user.data.UserRegistrationRequest;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import com.omega.jobportal.user.verificationCode.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;

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
        System.out.println("sending verification email...");
        emailService.sendSimpleMailMessage(user.getFirstName(), user.getEmail(), verificationCode);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userDtoMapper).toList();
    }

    public AppUser findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
