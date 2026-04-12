package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuEntity> findAll();
}
