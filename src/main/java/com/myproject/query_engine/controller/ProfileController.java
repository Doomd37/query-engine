package com.myproject.query_engine.controller;

import com.myproject.query_engine.dto.ProfileFilterRequest;
import com.myproject.query_engine.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @GetMapping
    public ResponseEntity<?> get(ProfileFilterRequest r) {
        return ResponseEntity.ok(service.getProfiles(r));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(service.search(q, page, limit));
    }
}
