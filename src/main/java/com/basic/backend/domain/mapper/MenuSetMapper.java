package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.MenuEntity;
import com.basic.backend.domain.entity.MenuSetEntity;
import com.basic.backend.domain.entity.vo.AdminMenuFlatRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuSetMapper {
    List<MenuSetEntity> findAll();
}