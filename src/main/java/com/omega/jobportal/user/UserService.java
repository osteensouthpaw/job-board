package com.omega.jobportal.user;

import com.omega.jobportal.config.SecurityUser;
import com.omega.jobportal.user.data.UserRegistrationRequest;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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
        boolean existsByEmail = userRepository.existsByEmail(request.email());
        if (existsByEmail)
            throw new BadCredentialsException("email already exists");

        //todo: userType is not nullable, yet it accepts null values
        AppUser appUser = new AppUser(request, passwordEncoder.encode(request.password()));
        AppUser user = userRepository.save(appUser);
        return userDtoMapper.apply(user);
    }

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
