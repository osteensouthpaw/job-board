package com.omega.jobportal.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.jobportal.auth.jwt.JwtService;
import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserRepository;
import com.omega.jobportal.user.UserType;
import com.omega.jobportal.user.data.UserResponse;
import com.omega.jobportal.user.dtoMapper.UserDtoMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserDtoMapper userDtoMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserType userType = getUserType(request);
        AppUser appUser = userRepository.findUserByEmail(oAuth2User.getAttribute("email")).orElse(null);
        log.error(userType.name());
        log.error(oAuth2User.getAttribute("email"));

        if (userType.equals(UserType.PENDING)) {
            response.sendRedirect("http://localhost:3000/auth/register");
            return;
        }

        appUser.setUserType(userType);
        UserResponse userResponse = userDtoMapper.apply(appUser);
        generateRefreshToken(response, userResponse);
        response.sendRedirect("http://localhost:3000/job-listings");
    }

    private void generateRefreshToken(HttpServletResponse response, UserResponse userResponse) {
        String refreshToken = jwtService.generateRefreshToken(userResponse);
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("api/v1/auth/refresh");
        cookie.setMaxAge(24 * 14 * 60 * 60);  //2wks
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    private static UserType getUserType(HttpServletRequest request) {
        String pendingRole = (String) request.getSession().getAttribute("PENDING_ROLE");
        request.getSession().removeAttribute("PENDING_ROLE");
        UserType userType = UserType.PENDING;
        if (pendingRole != null) {
            try {
                String decoded = new String(Base64.getDecoder().decode(pendingRole));
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> map = mapper.readValue(decoded, Map.class);
                userType = UserType.valueOf(map.get("role")); // e.g. JOB_SEEKER
            } catch (Exception e) {
                // log and fallback to default
            }
        }
        return userType;
    }
}
