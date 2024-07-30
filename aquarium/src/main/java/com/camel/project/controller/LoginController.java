
package com.camel.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camel.project.dto.Login;
import com.camel.project.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("l", new Login());
        return "login";
    }
    
    @PostMapping("/login-form")
    public String getLogin(Model model,
                           @RequestParam("memberId") String memberId,
                           @RequestParam("memberPw") String memberPw,
                           HttpSession session) {
        Login login = loginService.getLogin(memberId, memberPw);
        if (login != null) {
            session.setAttribute("loginSession", login);
            return "main"; 
        } else {
            model.addAttribute("msg", "일치하는 아이디 비밀번호가 없습니다.");
            model.addAttribute("l", new Login());
            return "login"; 
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}