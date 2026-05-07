package com.myproject.query_engine.service;

import com.myproject.query_engine.entity.RefreshToken;
import com.myproject.query_engine.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repo;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    public RefreshToken create(String userId) {

        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .userId(userId)
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();

        return repo.save(token);
    }

    public RefreshToken validate(String token) {

        RefreshToken refreshToken = repo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repo.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }
}