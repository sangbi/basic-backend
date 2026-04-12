package com.basic.backend.domain.test.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {

    void insert(@Param("message") String message);

    String findById(@Param("id") Long id);
}