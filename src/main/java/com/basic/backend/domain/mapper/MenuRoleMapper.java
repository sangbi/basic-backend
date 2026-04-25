package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.MenuRoleEntity;
import com.basic.backend.domain.entity.vo.MenuRoleFlatRow;
import com.basic.backend.domain.entity.vo.MyMenuPermissionFlatRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuRoleMapper {
    List<MenuRoleEntity> findAll();
    void updatePermissions(MenuRoleEntity entity);
    List<MenuRoleFlatRow> findAllWithRoleAndMenu();
    String findPermissionYn(
            @Param("permissionType") String permissionType,
            @Param("roleCode") String roleCode,
            @Param("apiPath") String apiPath
    );
    List<MyMenuPermissionFlatRow> findMyPermissionsByRoleCode(@Param("roleCode") String roleCode);

    String findPermissionYnByRequestUri(
            @Param("roleCode") String roleCode,
            @Param("requestUri") String requestUri,
            @Param("permissionType") String permissionType
    );
}