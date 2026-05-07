package com.myproject.query_engine.service;

import com.myproject.query_engine.dto.AuthResponse;
import com.myproject.query_engine.dto.GithubTokenResponse;
import com.myproject.query_engine.dto.GithubUserResponse;
import com.myproject.query_engine.entity.Role;
import com.myproject.query_engine.entity.User;
import com.myproject.query_engine.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GithubOAuthService {

    private final WebClient webClient;

    @Value("${github.client-id}")
    private String clientId;

    @Value("${github.client-secret}")
    private String clientSecret;

    @Value("${github.redirect-uri}")
    private String redirectUri;

    public GithubUserResponse getUser(String code) {

        GithubTokenResponse tokenResponse = webClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .header("Accept", "application/json")
                .bodyValue(Map.of(
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "code", code,
                        "redirect_uri", redirectUri
                ))
                .retrieve()
                .bodyToMono(GithubTokenResponse.class)
                .block();

        return webClient.get()
                .uri("https://api.github.com/user")
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                .retrieve()
                .bodyToMono(GithubUserResponse.class)
                .block();
    }
}