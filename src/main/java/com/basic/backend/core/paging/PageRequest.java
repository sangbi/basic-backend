package com.basic.backend.core.paging;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRequest<T> {

    private Integer page = 1;
    private Integer size = 10;
    private T condition;

    public int getOffset() {
        return (getSafePage() - 1) * getSafeSize();
    }

    public int getLimit() {
        return getSafeSize();
    }

    public int getSafePage() {
        return (page == null || page < 1) ? 1 : page;
    }

    public int getSafeSize() {
        if (size == null || size < 1) {
            return 10;
        }
        return Math.min(size, 100);
    }
}