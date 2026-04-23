package com.myproject.query_engine.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class PagedResponse<T> {

    private String status;
    private int page;
    private int limit;
    private long total;
    private T data;
}