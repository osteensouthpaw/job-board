package com.omega.jobportal.auth;

import com.omega.jobportal.user.UserService;
import com.omega.jobportal.user.data.UserRegistrationRequest;
import com.omega.jobportal.user.data.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody AuthRequest authRequest,
                      HttpServletResponse response,
                      HttpServletRequest request) {
        authenticationService.login(authRequest, request, response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRegistrationRequest request) {
        return ResponseEntity.ok().body(userService.createUser(request));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        authenticationService.logout(request, response, authentication);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getSession() {
        UserResponse loggedInUser = authenticationService.getSession();
        return ResponseEntity.ok().body(loggedInUser);
    }
}
