package com.myproject.query_engine.specification;

import com.myproject.query_engine.dto.ProfileFilterRequest;
import com.myproject.query_engine.entity.Profile;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProfileSpecification {

    public static Specification<Profile> build(ProfileFilterRequest req) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (req.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), req.getGender()));
            }

            if (req.getAgeGroup() != null) {
                predicates.add(cb.equal(root.get("ageGroup"), req.getAgeGroup()));
            }

            if (req.getCountryId() != null) {
                predicates.add(cb.equal(root.get("countryId"), req.getCountryId()));
            }

            if (req.getMinAge() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("age"), req.getMinAge()));
            }

            if (req.getMaxAge() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("age"), req.getMaxAge()));
            }

            if (req.getMinGenderProbability() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("genderProbability"), req.getMinGenderProbability()));
            }

            if (req.getMinCountryProbability() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("countryProbability"), req.getMinCountryProbability()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}