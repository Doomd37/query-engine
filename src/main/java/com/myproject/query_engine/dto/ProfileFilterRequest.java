package com.myproject.query_engine.dto;

import lombok.Data;

@Data
public class ProfileFilterRequest {

    private String gender;
    private String ageGroup;
    private String countryId;

    private Integer minAge;
    private Integer maxAge;

    private Double minGenderProbability;
    private Double minCountryProbability;

    private String sortBy;
    private String order;

    private Integer page = 1;
    private Integer limit = 10;
}