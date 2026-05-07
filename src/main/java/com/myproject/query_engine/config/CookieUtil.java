package com.myproject.query_engine.config;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public ResponseCookie createAccessCookie(String token) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(false) // set true in production HTTPS
                .path("/")
                .maxAge(15 * 60)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie createRefreshCookie(String token) {
        return ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie clearCookie(String name) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
    }
}
