package com.basic.backend.domain.service;

import com.basic.backend.domain.dto.response.MenuSetResponse;
import com.basic.backend.domain.mapper.MenuSetMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuSetService {
    private final MenuSetMapper menuSetMapper;

    public MenuSetService(MenuSetMapper menuSetMapper) {
        this.menuSetMapper = menuSetMapper;
    }

    public List<MenuSetResponse> findAll() {
        return menuSetMapper.findAll().stream()
                .map(row -> MenuSetResponse.builder()
                        .id(row.getId())
                        .menuSetCd(row.getMenuSetCd())
                        .menuSetNm(row.getMenuSetNm())
                        .description(row.getDescription())
                        .status(row.getStatus())
                        .build())
                .toList();
    }
}