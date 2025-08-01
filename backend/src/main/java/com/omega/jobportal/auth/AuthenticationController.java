package com.omega.jobportal.auth;

import com.omega.jobportal.auth.jwt.JwtService;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserService;
import com.omega.jobportal.user.data.ForgotPasswordRequest;
import com.omega.jobportal.user.data.UpdateUserPasswordRequest;
import com.omega.jobportal.user.data.UserRegistrationRequest;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import com.omega.jobportal.user.verificationCode.VerificationCodeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final VerificationCodeService verificationCodeService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest, HttpServletResponse response) {
        AuthResponse authResponse = authenticationService.login(authRequest);
        String refreshToken = jwtService.generateRefreshToken(authResponse.userResponse());
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("api/v1/auth/refresh");
        cookie.setMaxAge(24 * 14 * 60 * 60);  //2wks
        cookie.setSecure(true);
        response.addCookie(cookie);
        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken) {
        AppUser user = jwtService.validateTokenAndReturnUser(refreshToken);
        UserResponse userResponse = userDtoMapper.apply(user);
        String accessToken = jwtService.generateAccessToken(userResponse);
        AuthResponse authResponse = new AuthResponse(userResponse, accessToken);
        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRegistrationRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getSession() {
        AppUser loggedInUser = authenticationService.getSession();
        UserResponse userResponse = userDtoMapper.apply(loggedInUser);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping("verify-email")
    public ResponseEntity<Void> verificationToken(@RequestParam("token") String token) {
        verificationCodeService.verifyCode(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        userService.forgotPassword(request.email());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid UpdateUserPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }
}
