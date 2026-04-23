package com.myproject.query_engine.service;

import com.myproject.query_engine.dto.PagedResponse;
import com.myproject.query_engine.dto.ProfileFilterRequest;
import com.myproject.query_engine.entity.Profile;
import com.myproject.query_engine.exception.UnprocessableException;
import com.myproject.query_engine.parser.QueryParser;
import com.myproject.query_engine.repository.ProfileRepository;
import com.myproject.query_engine.specification.ProfileSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repo;
    private final QueryParser parser;

    private static final Set<String> ALLOWED_SORT =
            Set.of("age", "createdAt", "genderProbability");

    public PagedResponse<List<Profile>> getProfiles(ProfileFilterRequest r) {

        validate(r);

        Pageable pageable = PageRequest.of(
                r.getPage() - 1,
                r.getLimit(),
                buildSort(r)
        );

        Page<Profile> result =
                repo.findAll(ProfileSpecification.build(r), pageable);

        return new PagedResponse<>(
                r.getPage(),
                r.getLimit(),
                result.getTotalElements(),
                result.getContent()
        );
    }

    public PagedResponse<List<Profile>> search(String q, int page, int limit) {

        ProfileFilterRequest r = parser.parse(q);
        r.setPage(page);
        r.setLimit(limit);

        return getProfiles(r);
    }

    private Sort buildSort(ProfileFilterRequest r) {

        String sortBy = r.getSortBy() == null ? "createdAt" : r.getSortBy();

        if (!ALLOWED_SORT.contains(sortBy))
            throw new UnprocessableException("Invalid query parameters");

        String order = r.getOrder() == null ? "asc" : r.getOrder();

        return Sort.by(Sort.Direction.fromString(order), sortBy);
    }

    private void validate(ProfileFilterRequest r) {

        if (r.getLimit() > 50 || r.getLimit() < 1)
            throw new UnprocessableException("Invalid query parameters");

        if (r.getPage() < 1)
            throw new UnprocessableException("Invalid query parameters");
    }
}
