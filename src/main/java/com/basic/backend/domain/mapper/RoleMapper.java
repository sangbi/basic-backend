package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<RoleEntity> findAll();
}
