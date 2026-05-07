package com.myproject.query_engine.dto;

import lombok.Data;

@Data
public class GithubUserResponse {

    private Long id;

    private String login;

    private String email;
}
