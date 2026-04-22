package com.myproject.query_engine.dto;

import lombok.Getter;

@Getter
public class PagedResponse<T> {

    private final String status = "success";
    private final int page;
    private final int limit;
    private final long total;
    private final T data;

    public PagedResponse(int page, int limit, long total, T data) {
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.data = data;
    }
}
