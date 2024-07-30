package com.camel.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camel.project.dto.Login;
import com.camel.project.mapper.LoginMapper;

@Service
public class LoginService {
    
    @Autowired
    private LoginMapper memberMapper;

    public Login getLogin(String memberId, String memberPw) {
        return memberMapper.getLogin(memberId, memberPw);
    }
}