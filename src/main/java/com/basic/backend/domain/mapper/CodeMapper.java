package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.CodeEntity;
import com.basic.backend.domain.entity.CodeGroupEntity;
import com.basic.backend.domain.entity.vo.CodeFlatRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeMapper {
    List<CodeGroupEntity> findAllGroups();
    CodeGroupEntity findGroupById(Long id);
    void insertGroup(CodeGroupEntity entity);
    void updateGroup(CodeGroupEntity entity);

    List<CodeFlatRow> findAllCodes();
    List<CodeFlatRow> findCodesByGroupCode(@Param("groupCode") String groupCode);
    CodeEntity findCodeById(Long id);
    void insertCode(CodeEntity entity);
    void updateCode(CodeEntity entity);
}