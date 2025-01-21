package com.omega.jobportal.auth;

import com.omega.jobportal.config.SecurityUser;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextLogoutHandler securityContextLogoutHandler;
    private final UserDtoMapper userDtoMapper;

    public void login(AuthRequest authRequest,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        var authToken = UsernamePasswordAuthenticationToken.unauthenticated(authRequest.email(), authRequest.password());
        var authentication = authenticationManager.authenticate(authToken);
        var securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        var context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }

    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        securityContextLogoutHandler.logout(request, response, authentication);
    }

    public UserResponse getSession() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof SecurityUser loggedInUser) {
            return userDtoMapper.apply(loggedInUser.getUser());
        } else throw new IllegalStateException("no user session");
    }
}
