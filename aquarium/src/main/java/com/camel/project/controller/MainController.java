package com.camel.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    
	//메인 페이지 이동
    @GetMapping("/")
    public String mainPage() {
        return "main"; // main.html 등의 뷰 이름을 반환
    }
	
    //전시생물소개 페이지 이동
    @GetMapping("/introducing_fish")
    public String introducingFishPage() {
    	return "introducing_fish";
    }
    
    //프로그램소개 페이지 이동
    @GetMapping("/introducing_program")
    public String introducingProgramPage() {
    	return "introducing_program";
    }
    
    //이용 안내 페이지 이동
    @GetMapping("/information_aquarium")
    public String informationPage() {
    	return "information_aquarium";
    }
    
}
