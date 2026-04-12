package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.MenuEntity;
import com.basic.backend.domain.entity.vo.AdminMenuFlatRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuEntity> findAll();
    List<AdminMenuFlatRow> findMenusByRoleCode(@Param("roleCode") String roleCode);
    MenuEntity findById(Long Id);
    void insert(MenuEntity entity);
    void update(MenuEntity entity);
}
