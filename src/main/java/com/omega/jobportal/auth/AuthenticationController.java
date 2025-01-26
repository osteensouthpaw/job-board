package com.omega.jobportal.auth;

import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserService;
import com.omega.jobportal.user.data.UserRegistrationRequest;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/login")
    public void login(@RequestBody @Valid AuthRequest authRequest,
                      HttpServletResponse response,
                      HttpServletRequest request) {
        authenticationService.login(authRequest, request, response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRegistrationRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        authenticationService.logout(request, response, authentication);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getSession() {
        AppUser loggedInUser = authenticationService.getSession();
        UserResponse userResponse = userDtoMapper.apply(loggedInUser);
        return ResponseEntity.ok().body(userResponse);
    }
}
