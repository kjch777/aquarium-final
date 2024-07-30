package com.camel.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camel.project.dto.Login;

@Mapper
public interface LoginMapper {
    Login getLogin(@Param("memberId") String memberId, 
                   @Param("memberPw") String memberPw);
}