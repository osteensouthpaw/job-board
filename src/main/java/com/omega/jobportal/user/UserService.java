package com.omega.jobportal.user;

import com.omega.jobportal.user.data.UserRegistrationRequest;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserResponse createUser(UserRegistrationRequest request) {
        //TODO: remember to hash Password
        AppUser user = AppUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .gender(request.gender())
                .imageUrl(request.imageUrl())
                .email(request.email())
                .password(request.password())
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
}
