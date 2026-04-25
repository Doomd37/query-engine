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

    private String name;
    private String gender;

    private Double genderProbability;
    private Integer age;

    private String ageGroup;
    private String countryId;
    private String countryName;

    private Double countryProbability;
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UuidCreator.getTimeOrderedEpoch();
        if (createdAt == null) createdAt = Instant.now();
    }
}