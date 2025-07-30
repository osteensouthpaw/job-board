package com.omega.jobportal.auth;

import com.omega.jobportal.auth.jwt.JwtService;
import com.omega.jobportal.config.SecurityUser;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDtoMapper userDtoMapper;
    private final JwtService jwtService;

    public AuthResponse login(AuthRequest authRequest) {
        var authToken = UsernamePasswordAuthenticationToken
                .unauthenticated(authRequest.email(), authRequest.password());
        var authentication = authenticationManager.authenticate(authToken);
        SecurityUser authenticatedUser = (SecurityUser) authentication.getPrincipal();
        String token = jwtService.createToken(authentication);
        UserResponse userResponse = userDtoMapper.apply(authenticatedUser.getUser());
        return new AuthResponse(userResponse, token);
    }

    public AppUser getSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new ApiException("No authenticated user found", HttpStatus.UNAUTHORIZED);
        }

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return securityUser.getUser();
    }
}
