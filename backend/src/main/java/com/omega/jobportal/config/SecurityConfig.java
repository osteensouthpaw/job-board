package com.omega.jobportal.config;

import com.omega.jobportal.auth.CustomOauth2UserService;
import com.omega.jobportal.auth.GlobalAuthenticationEntryPoint;
import com.omega.jobportal.auth.Oauth2LoginSuccessHandler;
import com.omega.jobportal.auth.jwt.CustomBearerTokenAccessDeniedHandler;
import com.omega.jobportal.auth.jwt.JwtAuthenticationConverter;
import com.omega.jobportal.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.omega.jobportal.user.UserType.JOB_SEEKER;
import static com.omega.jobportal.user.UserType.RECRUITER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final GlobalAuthenticationEntryPoint globalAuthenticationEntryPoint;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final CustomOauth2UserService customOauth2UserService;
    private final UserRepository userRepository;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;

    private final String[] AUTH_ROUTES = new String[]{
            "/api/v1/auth/**",
            "/oauth2/authorization/**",
            "/login/**",
    };

    private final String[] WHITE_LIST = new String[]{
            "/api/v1/job-posts",
            "/api/v1/job-posts/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };

    private final String[] JOB_SEEKER_ROUTES = new String[]{
            "/api/v1/job-seeker/**",
    };

    private final String[] RECRUITER_ROUTES = new String[]{
            "/api/v1/recruiters/organizations",
            "/api/v1/job-applications/reject",
            "/api/v1/job-applications/accept",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers(AUTH_ROUTES).permitAll()
                .requestMatchers(GET, WHITE_LIST).permitAll()
                .requestMatchers(POST, JOB_SEEKER_ROUTES).hasRole(JOB_SEEKER.name())
                .requestMatchers(PATCH, JOB_SEEKER_ROUTES).hasRole(JOB_SEEKER.name())
                .requestMatchers(DELETE, JOB_SEEKER_ROUTES).hasRole(JOB_SEEKER.name())
                .requestMatchers(RECRUITER_ROUTES).hasRole(RECRUITER.name())
                .anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(customOauth2UserService))
                .successHandler(oauth2LoginSuccessHandler)
        );
        http.exceptionHandling(customizer ->
                customizer.authenticationEntryPoint(globalAuthenticationEntryPoint)
        );
        http.oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter))
                        .accessDeniedHandler(customBearerTokenAccessDeniedHandler)
                        .authenticationEntryPoint(globalAuthenticationEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        var authenticationProvider = new DaoAuthenticationProvider(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "https://job-portal-frontend-nine-black.vercel.app"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

