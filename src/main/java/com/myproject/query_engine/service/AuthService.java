package com.myproject.query_engine.service;

import com.myproject.query_engine.config.JwtUtil;
import com.myproject.query_engine.dto.AuthResponse;
import com.myproject.query_engine.entity.RefreshToken;
import com.myproject.query_engine.entity.User;
import com.myproject.query_engine.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshRepo;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public AuthResponse generateTokens(User user) {

        String accessToken = jwtUtil.generateAccessToken(
                user.getGithubId(),   // 🔥 IMPORTANT CHANGE
                user.getRole().name()
        );

        String refreshToken = UUID.randomUUID().toString();

        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .userId(user.getId())
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();

        refreshRepo.save(token);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole().name())
                .build();
    }
}
