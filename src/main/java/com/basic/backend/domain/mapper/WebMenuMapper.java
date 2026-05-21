package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.vo.WebMenuFlatRow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WebMenuMapper {
    List<WebMenuFlatRow> findMenus();
}
