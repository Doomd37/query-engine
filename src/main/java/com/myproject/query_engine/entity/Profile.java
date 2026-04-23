package com.myproject.query_engine.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
import com.github.f4b6a3.uuid.UuidCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private String gender;

    @JsonProperty("gender_probability")
    private Double genderProbability;

    private Integer age;

    @JsonProperty("age_group")
    private String ageGroup;

    @JsonProperty("country_id")
    private String countryId;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("country_probability")
    private Double countryProbability;

    @JsonProperty("created_at")
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UuidCreator.getTimeOrderedEpoch(); // UUID v7
        if (createdAt == null) createdAt = Instant.now();
    }
}