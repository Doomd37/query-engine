package com.myproject.query_engine.service;

import com.myproject.query_engine.config.JwtUtil;
import com.myproject.query_engine.dto.AuthResponse;
import com.myproject.query_engine.dto.GithubUserResponse;
import com.myproject.query_engine.entity.RefreshToken;
import com.myproject.query_engine.entity.Role;
import com.myproject.query_engine.entity.User;
import com.myproject.query_engine.repository.RefreshTokenRepository;
import com.myproject.query_engine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GithubOAuthService githubOAuthService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshRepo;
    private final UserRepository userRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    // GITHUB LOGIN FLOW

    public AuthResponse loginWithGithub(String code) {

        GithubUserResponse githubUser = githubOAuthService.getUser(code);

        User user = userRepository.findByGithubId(String.valueOf(githubUser.getId()))
                .orElseGet(() -> createUser(githubUser));

        return generateTokens(user);
    }

    // TOKEN GENERATION

    public AuthResponse generateTokens(User user) {

        String accessToken = jwtUtil.generateAccessToken(
                String.valueOf(user.getId()),
                user.getRole().name()
        );

        String refreshToken = UUID.randomUUID().toString();

        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .userId(String.valueOf(user.getId()))
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();

        refreshRepo.save(token);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole().name())
                .build();
    }

    // REFRESH FLOW

    public Map<String, String> refresh(String refreshTokenValue) {

        RefreshToken token = refreshTokenService.validate(refreshTokenValue);

        User user = userRepository.findById(token.getUserId())
                .orElseThrow();

        String newAccessToken = jwtUtil.generateAccessToken(
                String.valueOf(user.getId()),
                user.getRole().name()
        );

        return Map.of("accessToken", newAccessToken);
    }

    // CREATE USER

    private User createUser(GithubUserResponse githubUser) {
        return userRepository.save(
                User.builder()
                        .githubId(String.valueOf(githubUser.getId()))
                        .username(githubUser.getLogin())
                        .email(githubUser.getEmail())
                        .role(Role.ANALYST)
                        .build()
        );
    }
}