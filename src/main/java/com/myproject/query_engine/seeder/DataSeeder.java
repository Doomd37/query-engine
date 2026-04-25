package com.myproject.query_engine.seeder;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.myproject.query_engine.entity.Profile;
import com.myproject.query_engine.repository.ProfileRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Nonnull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProfileRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    public DataSeeder(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(@Nonnull String... args) throws Exception {

        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        InputStream input = getClass().getResourceAsStream("/seed_profiles.json");

        if (input == null) {
            throw new RuntimeException("profiles.json not found in resources folder");
        }

        JsonNode root = mapper.readTree(input);
        JsonNode profilesNode = root.get("profiles");

        if (profilesNode == null || !profilesNode.isArray()) {
            throw new RuntimeException("Invalid JSON format: 'profiles' array missing");
        }

        List<Profile> profiles = mapper.readValue(
                profilesNode.toString(),
                new TypeReference<List<Profile>>() {}
        );

        for (Profile p : profiles) {
            if (!repository.existsByName(p.getName())) {
                repository.save(p);
            }
        }

        System.out.println("✅ Database seeding completed: " + profiles.size() + " profiles loaded");
    }
}