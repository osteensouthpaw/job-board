package com.omega.jobportal.config;

import com.omega.jobportal.auth.CustomOauth2UserService;
import com.omega.jobportal.auth.GlobalAuthenticationEntryPoint;
import com.omega.jobportal.auth.Oauth2LoginSuccessHandler;
import com.omega.jobportal.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final GlobalAuthenticationEntryPoint globalAuthenticationEntryPoint;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final CustomOauth2UserService customOauth2UserService;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/api/v1/auth/**", "/login/**", "/**", "/oauth2/authorization/**").permitAll()
                .anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOauth2UserService))
                        .successHandler(oauth2LoginSuccessHandler)
                );

        http.exceptionHandling(customizer ->
                customizer.authenticationEntryPoint(globalAuthenticationEntryPoint)
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        var authenticationProvider = new DaoAuthenticationProvider(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
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

}
