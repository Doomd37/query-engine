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

    @Value("${github.client-id}")
    private String clientId;

    @Value("${github.client-secret}")
    private String clientSecret;

    @Value("${github.redirect-uri}")
    private String redirectUri;

    private final WebClient webClient;
    private final UserRepository userRepository;
    private final AuthService authService;

    public AuthResponse login(String code) {

        GithubTokenResponse tokenResponse =
                webClient.post()
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

        GithubUserResponse githubUser =
                webClient.get()
                        .uri("https://api.github.com/user")
                        .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                        .retrieve()
                        .bodyToMono(GithubUserResponse.class)
                        .block();

        User user = userRepository
                .findByGithubId(String.valueOf(githubUser.getId()))
                .orElseGet(() -> {

                    Role role = Role.ANALYST;

                    return userRepository.save(
                            User.builder()
                                    .githubId(String.valueOf(githubUser.getId()))
                                    .username(githubUser.getLogin())
                                    .email(githubUser.getEmail())
                                    .role(role)
                                    .build()
                    );
                });

        return authService.generateTokens(user);
    }
}
