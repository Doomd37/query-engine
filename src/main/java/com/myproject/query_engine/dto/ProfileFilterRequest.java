package com.myproject.query_engine.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ProfileFilterRequest {

    private String gender;

    @JsonProperty("age_group")
    private String ageGroup;

    @JsonProperty("country_id")
    private String countryId;

    @JsonProperty("min_age")
    private Integer minAge;

    @JsonProperty("max_age")
    private Integer maxAge;

    @JsonProperty("min_gender_probability")
    private Double minGenderProbability;

    @JsonProperty("min_country_probability")
    private Double minCountryProbability;

    private String sortBy;
    private String order;

    private Integer page = 1;
    private Integer limit = 10;
}