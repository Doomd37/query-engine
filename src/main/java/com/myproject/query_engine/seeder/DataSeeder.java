package com.myproject.query_engine.seeder;

import com.myproject.query_engine.entity.Profile;
import com.myproject.query_engine.repository.ProfileRepository;
import jakarta.annotation.Nonnull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProfileRepository repository;
    private final ObjectMapper mapper;

    public DataSeeder(ProfileRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void run(@Nonnull String... args) throws Exception {

        InputStream input = getClass().getResourceAsStream("/profiles.json");

        List<Profile> profiles = new ArrayList<>(
                Arrays.asList(mapper.readValue(input, Profile[].class))
        );

        for (Profile p : profiles) {
            if (!repository.existsByName(p.getName())) {
                repository.save(p);
            }
        }
    }
}
