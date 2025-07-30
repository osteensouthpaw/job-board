package com.omega.jobportal.auth.jwt;

import com.omega.jobportal.config.SecurityUser;
import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final UserRepository userRepository;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String userId = jwt.getClaimAsString("userId");

        AppUser user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        SecurityUser securityUser = new SecurityUser(user);
        Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();

        return new JwtAuthenticationToken(jwt, authorities, securityUser.getUsername()) {
            @Override
            public Object getPrincipal() {
                return securityUser;
            }
        };
    }
}
