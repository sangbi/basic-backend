package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.MenuRoleEntity;
import com.basic.backend.domain.entity.vo.MenuRoleFlatRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuRoleMapper {
    List<MenuRoleEntity> findAll();
    void updatePermissions(MenuRoleEntity entity);
    List<MenuRoleFlatRow> findAllWithRoleAndMenu();

    String findPermissionYn(
            @Param("roleCode") String roleCode,
            @Param("apiPath") String apiPath,
            @Param("permissionType") String permissionType
    );
}