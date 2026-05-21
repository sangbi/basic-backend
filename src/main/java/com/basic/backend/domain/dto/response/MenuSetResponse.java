package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuSetResponse {
    private Long id;
    private String menuSetCd;
    private String menuSetNm;
    private String description;
    private String status;
}