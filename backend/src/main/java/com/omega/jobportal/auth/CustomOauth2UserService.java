package com.omega.jobportal.auth;

import com.omega.jobportal.user.AppUser;
import com.omega.jobportal.user.UserRepository;
import com.omega.jobportal.user.UserType;
import com.omega.jobportal.user.userConnectedAccount.UserConnectedAccount;
import com.omega.jobportal.user.userConnectedAccount.UserConnectedAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {
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
        Map<String, Object> updatedAttributes = new HashMap<>(oAuth2User.getAttributes());
        final String emailAddress = extractPrimaryEmailAddress(
                oAuth2User,
                userRequest.getAccessToken().getTokenValue()
        );

        if (emailAddress == null)
            throw new OAuth2AuthenticationException("unable to fetch email");

        updatedAttributes.put(EMAIL_KEY, emailAddress);
        createUserAndConnectAccount(providerId, provider, updatedAttributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(UserType.PENDING.name())),
                updatedAttributes,
                NAME_ATTRIBUTE
        );
    }

    public void createUser(String providerId, String provider, Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        AppUser appUser = new AppUser(name, email);
        appUser = userRepository.save(appUser);
        connectAccount(providerId, provider, appUser);
    }

    private void createUserAndConnectAccount(String providerId, String provider, Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        Optional<UserConnectedAccount> userConnectedAccount = userConnectedAccountRepository
                .findByProviderIdAndProvider(providerId, provider);
        if (userConnectedAccount.isEmpty()) {
            userRepository.findUserByEmail(email)
                    .ifPresentOrElse(user -> connectAccount(providerId, provider, user),
                            () -> createUser(providerId, provider, attributes)
                    );
        }
    }

    private String extractPrimaryEmailAddress(OAuth2User oauth2User, String token) {
        String primaryEmailAddress = oauth2User.getAttribute(EMAIL_KEY);

        if (!(primaryEmailAddress == null || primaryEmailAddress.isBlank())) {
            return primaryEmailAddress;
        }

        return emailFetcher.fetchPrimaryEmailAddress(token);
    }

    private void connectAccount(String providerId, String provider, AppUser appUser) {
        UserConnectedAccount newUserConnectedAccount = new UserConnectedAccount(providerId, provider, appUser);
        userConnectedAccountRepository.save(newUserConnectedAccount);
    }
}
