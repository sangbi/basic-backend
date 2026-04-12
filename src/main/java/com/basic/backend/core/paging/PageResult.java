package com.basic.backend.core.paging;

import java.util.List;

public class PageResult {

    private PageResult() {
    }

    public static <T, C> PageResponse<T> of(
            List<T> items,
            PageRequest<C> request,
            long totalCount
    ) {
        int page = request.getSafePage();
        int size = request.getSafeSize();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return PageResponse.<T>builder()
                .items(items)
                .page(page)
                .size(size)
                .totalCount(totalCount)
                .totalPages(totalPages)
                .build();
    }
}