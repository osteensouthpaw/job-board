package com.omega.jobportal.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextLogoutHandler securityContextLogoutHandler;

    public void login(AuthRequest authRequest,
                      HttpServletRequest request,
                      HttpServletResponse response)  {
        var authToken = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password());
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
}
