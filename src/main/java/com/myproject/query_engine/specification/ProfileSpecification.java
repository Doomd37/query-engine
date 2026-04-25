package com.myproject.query_engine.specification;

import com.myproject.query_engine.dto.ProfileFilterRequest;
import com.myproject.query_engine.entity.Profile;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProfileSpecification {

    public static Specification<Profile> build(ProfileFilterRequest r) {
        return (root, query, cb) -> {

            List<Predicate> p = new ArrayList<>();

            // Gender (case-insensitive)
            if (r.getGender() != null) {
                p.add(cb.equal(
                        cb.lower(root.get("gender")),
                        r.getGender().toLowerCase()
                ));
            }

            // Age group (case-insensitive)
            if (r.getAgeGroup() != null) {
                p.add(cb.equal(
                        cb.lower(root.get("ageGroup")),
                        r.getAgeGroup().toLowerCase()
                ));
            }

            // Country ID (case-insensitive)
            if (r.getCountryId() != null) {
                p.add(cb.equal(
                        cb.upper(root.get("countryId")),
                        r.getCountryId().toUpperCase()
                ));
            }

            // Age filters
            if (r.getMinAge() != null) {
                p.add(cb.greaterThanOrEqualTo(root.get("age"), r.getMinAge()));
            }

            if (r.getMaxAge() != null) {
                p.add(cb.lessThanOrEqualTo(root.get("age"), r.getMaxAge()));
            }

            // Gender probability (STRICT >=)
            if (r.getMinGenderProbability() != null) {
                p.add(cb.greaterThanOrEqualTo(
                        root.get("genderProbability"),
                        r.getMinGenderProbability()
                ));
            }

            // Country probability (STRICT >=)
            if (r.getMinCountryProbability() != null) {
                p.add(cb.greaterThanOrEqualTo(
                        root.get("countryProbability"),
                        r.getMinCountryProbability()
                ));
            }

            return cb.and(p.toArray(new Predicate[0]));
        };
    }
}