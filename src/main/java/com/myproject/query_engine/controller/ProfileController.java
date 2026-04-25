package com.myproject.query_engine.controller;

import com.myproject.query_engine.dto.ProfileFilterRequest;
import com.myproject.query_engine.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false, name = "age_group") String ageGroup,
            @RequestParam(required = false, name = "country_id") String countryId,
            @RequestParam(required = false, name = "min_age") Integer minAge,
            @RequestParam(required = false, name = "max_age") Integer maxAge,
            @RequestParam(required = false, name = "min_gender_probability") Double minGenderProbability,
            @RequestParam(required = false, name = "min_country_probability") Double minCountryProbability,
            @RequestParam(required = false, name = "sort_by") String sortBy,
            @RequestParam(required = false) String order,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {

        ProfileFilterRequest r = new ProfileFilterRequest();

        r.setGender(gender);
        r.setAgeGroup(ageGroup);
        r.setCountryId(countryId);
        r.setMinAge(minAge);
        r.setMaxAge(maxAge);
        r.setMinGenderProbability(minGenderProbability);
        r.setMinCountryProbability(minCountryProbability);
        r.setSortBy(sortBy);
        r.setOrder(order);
        r.setPage(page);
        r.setLimit(limit);

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