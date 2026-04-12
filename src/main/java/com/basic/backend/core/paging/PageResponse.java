package com.basic.backend.core.paging;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageResponse<T> {
    private List<T> items;
    private int page;
    private int size;
    private long totalCount;
    private int totalPages;
}