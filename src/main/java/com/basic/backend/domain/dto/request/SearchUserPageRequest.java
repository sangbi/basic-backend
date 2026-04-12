package com.basic.backend.domain.dto.request;

import com.basic.backend.core.paging.PageRequest;
import lombok.*;

@Getter
@Setter
public class SearchUserPageRequest extends PageRequest<SearchUserCondition> {

    public SearchUserPageRequest() {
        setCondition(new SearchUserCondition());
    }
}
