package com.myproject.query_engine.repository;

import com.myproject.query_engine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByGithubId(String githubId);
}