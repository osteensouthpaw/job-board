package com.omega.jobportal.auth;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public record GitHubEmailFetcher(RestClient restClient) {
    private static final String EMAILS_URL = "https://api.github.com/user/emails";
    private static final String BEARER_PREFIX = "Bearer ";

    public String fetchPrimaryEmailAddress(String token) {
        List<GithubEmail> emailVmList = restClient
                .get()
                .uri(EMAILS_URL)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token)
                .header(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (emailVmList == null || emailVmList.isEmpty()) {
            return null;
        }

        return emailVmList.stream()
                .filter(GithubEmail::primary)
                .findFirst()
                .map(GithubEmail::email)
                .orElse(null);
    }

    private record GithubEmail(String email, Boolean primary) {
    }
}