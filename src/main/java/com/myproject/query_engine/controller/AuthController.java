package com.myproject.query_engine.controller;

import com.myproject.query_engine.config.CookieUtil;
import com.myproject.query_engine.dto.AuthResponse;
import com.myproject.query_engine.service.AuthService;
import com.myproject.query_engine.service.GithubOAuthService;
import com.myproject.query_engine.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private CookieUtil cookieUtil;

    @GetMapping("/github/callback")
    public ResponseEntity<AuthResponse> githubCallback(@RequestParam String code) {
        return ResponseEntity.ok(authService.loginWithGithub(code));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    @PostMapping("/login/success")
    public ResponseEntity<AuthResponse> loginSuccess(@RequestParam String code) {

        AuthResponse response = authService.loginWithGithub(code);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        cookieUtil.createAccessCookie(response.getAccessToken()).toString())
                .header(HttpHeaders.SET_COOKIE,
                        cookieUtil.createRefreshCookie(response.getRefreshToken()).toString())
                .body(response);
    }
}