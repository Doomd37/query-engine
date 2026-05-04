package com.myproject.query_engine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // 🔥 PRIMARY IDENTITY FROM GITHUB
    @Column(unique = true, nullable = false)
    private String githubId;

    // Optional but useful
    private String username;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}