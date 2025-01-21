package com.omega.jobportal.user;

import com.omega.jobportal.config.SecurityUser;
import com.omega.jobportal.user.data.UserRegistrationRequest;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRegistrationRequest request) {
        AppUser user = AppUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .gender(request.gender())
                .imageUrl(request.imageUrl())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        return userDtoMapper.apply(userRepository.save(user));
    }

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public AppUser findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
