package com.omega.jobportal.config;

import com.omega.jobportal.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        http.oauth2Login(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());
        http.exceptionHandling(customizer -> {
            customizer.authenticationEntryPoint(
                    (request, response, authException) -> {
                        response.sendError(401, "Unauthorized");
                    });
        });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        var authenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityContextRepository SecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }
}
