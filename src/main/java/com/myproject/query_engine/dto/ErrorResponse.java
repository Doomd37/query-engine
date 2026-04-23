package com.myproject.query_engine.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

@JsonPropertyOrder({ "status", "message" })
public class ErrorResponse {
    private final String status = "error";
    private final String message;
}
