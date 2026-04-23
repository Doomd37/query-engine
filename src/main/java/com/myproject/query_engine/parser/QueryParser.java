package com.myproject.query_engine.parser;

import com.myproject.query_engine.dto.ProfileFilterRequest;
import com.myproject.query_engine.exception.BadRequestException;
import com.myproject.query_engine.exception.UnprocessableException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class QueryParser {

    private static final Map<String, String> COUNTRY = Map.of(
            "nigeria", "NG",
            "kenya", "KE",
            "angola", "AO"
    );

    public ProfileFilterRequest parse(String input) {

        if (input == null || input.isBlank())
            throw new BadRequestException("Missing query parameter");

        String q = input.toLowerCase();
        ProfileFilterRequest r = new ProfileFilterRequest();

        boolean matched = false;

        if (q.contains("male")) { r.setGender("male"); matched = true; }
        if (q.contains("female")) { r.setGender("female"); matched = true; }

        if (q.contains("young")) {
            r.setMinAge(16);
            r.setMaxAge(24);
            matched = true;
        }

        if (q.contains("adult")) { r.setAgeGroup("adult"); matched = true; }
        if (q.contains("teenager")) { r.setAgeGroup("teenager"); matched = true; }

        for (var e : COUNTRY.entrySet()) {
            if (q.contains(e.getKey())) {
                r.setCountryId(e.getValue());
                matched = true;
            }
        }

        Matcher m = Pattern.compile("above (\\d+)").matcher(q);
        if (m.find()) {
            r.setMinAge(Integer.parseInt(m.group(1)));
            matched = true;
        }

        if (!matched)
            throw new UnprocessableException("Unable to interpret query");

        return r;
    }
}
