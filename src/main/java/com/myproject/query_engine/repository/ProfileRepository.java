package com.myproject.query_engine.repository;

import com.myproject.query_engine.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProfileRepository extends
        JpaRepository<Profile, UUID>,
        JpaSpecificationExecutor<Profile> {

    boolean existsByName(String name);
}
