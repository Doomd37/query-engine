package com.myproject.query_engine.controller;

import com.myproject.query_engine.dto.AuthResponse;
import com.myproject.query_engine.service.GithubOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GithubOAuthService githubOAuthService;

    @GetMapping("/github/callback")
    public ResponseEntity<AuthResponse> githubCallback(
            @RequestParam String code
    ) {
        return ResponseEntity.ok(
                githubOAuthService.login(code)
        );
    }
}
