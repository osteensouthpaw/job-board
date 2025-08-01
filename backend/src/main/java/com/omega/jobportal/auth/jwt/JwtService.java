package com.omega.jobportal.auth.jwt;

import com.omega.jobportal.exception.ApiException;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserRepository;
import com.omega.jobportal.user.data.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final JwtDecoder jwtDecoder;

    public String generateAccessToken(UserResponse user) {
        long expiresAt = 1; //1hr
        return generateAccessToken(user, expiresAt);
    }

    public String generateRefreshToken(UserResponse userResponse) {
        long expiresAt = 24 * 14; //2wks
        return generateAccessToken(userResponse, expiresAt);
    }

    private String generateAccessToken(UserResponse user, long expirationDuration) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .subject(user.email())
                .expiresAt(Instant.now().plus(expirationDuration, ChronoUnit.MINUTES))
                .claim("userId", user.id())
                .claim("role", user.userType())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public AppUser validateTokenAndReturnUser(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        String userId = jwt.getClaimAsString("userId");
        return userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.UNAUTHORIZED));
    }
}
