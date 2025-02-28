package com.omega.jobportal.auth;

import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserRepository;
import com.omega.jobportal.user.userConnectedAccount.UserConnectedAccount;
import com.omega.jobportal.user.userConnectedAccount.UserConnectedAccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private static final Logger log = LoggerFactory.getLogger(CustomOauth2UserService.class);
    private final UserConnectedAccountRepository userConnectedAccountRepository;
    private final UserRepository userRepository;
    private final GitHubEmailFetcher emailFetcher;
    private static final String NAME_ATTRIBUTE = "login";
    private static final String EMAIL_KEY = "email";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");
        oAuth2User.getAttributes().forEach((key, value) -> log.info("key {}, value {}", key, value));

        Optional<UserConnectedAccount> userConnectedAccount = userConnectedAccountRepository.findByProviderIdAndProvider(providerId, provider);
        if (userConnectedAccount.isEmpty()) {
            userRepository.findUserByEmail(email)
                    .ifPresentOrElse(user -> connectAccount(providerId, provider, user),
                            () -> createUser(providerId, provider, oAuth2User)
                    );
        }
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(null)),
                oAuth2User.getAttributes(),
                NAME_ATTRIBUTE
        );
    }


    private String extractPrimaryEmailAddress(
            OAuth2User oauth2User,
            String token) {
        String primaryEmailAddress = oauth2User.getAttribute(EMAIL_KEY);

        if (!(primaryEmailAddress == null || primaryEmailAddress.isBlank())) {
            return primaryEmailAddress;
        }

        return emailFetcher.fetchPrimaryEmailAddress(token);
    }

    public void createUser(String providerId, String provider, OAuth2User oAuth2User) {
        AppUser appUser = new AppUser(oAuth2User);
        appUser = userRepository.save(appUser);
        connectAccount(providerId, provider, appUser);
    }

    private void connectAccount(String providerId, String provider, AppUser appUser) {
        UserConnectedAccount newUserConnectedAccount = new UserConnectedAccount(providerId, provider, appUser);
        userConnectedAccountRepository.save(newUserConnectedAccount);
    }
}
