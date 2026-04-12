package com.basic.backend.domain.mapper;

import com.basic.backend.domain.dto.request.SearchUserRequest;
import com.basic.backend.domain.dto.request.UserUpdateRequest;
import com.basic.backend.domain.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserEntity findByUserId(String userId);
    void insert(UserEntity userEntity);
    void update(UserUpdateRequest userUpdateRequest);
    void delete(String userId);
    List<UserEntity> findAllPaged(SearchUserRequest pageRequest);
    long countAll(SearchUserRequest pageRequest);
}