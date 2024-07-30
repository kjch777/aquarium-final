package com.camel.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camel.project.dto.Login;
import com.camel.project.dto.Member;
import com.camel.project.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
    //회원가입 페이지 이동
	@GetMapping("/registerForm")
	public String RegisterForm(Model model) {
		model.addAttribute("member", new Member());		
		return "registerForm";
	}
	
	@Autowired
	private MemberService memberService;
	
	//회원가입 페이지 폼 조종
	@PostMapping("/registerForm")
	public String insertMember(Member member, Model model) {
		
		memberService.insertMember(member);
		return "redirect:/login";
	}
	
	//이메일인증
	@ResponseBody
	@PostMapping("/mail")
	public String mailSend(String mail) {
		int number = memberService.sendMail(mail);
		String num = "" + number; //랜덤으로 생성된 숫자 가져오기
		
		//내가 전달받은 숫자가 맞는지 확인용으로 가져옴
		return num;
	}
	
	//아이디 중복 검사
    @PostMapping("/checkId")
    @ResponseBody
    public ResponseEntity<Boolean> checkId(@RequestParam("memberId") String memberId) {
        boolean result = false;

        // 입력된 memberId가 비어있는지 검사
        if (memberId.trim().isEmpty()) {
        	result = false;
        } 
        else {
            // memberId가 비어있지 않으면 서비스 계층에서 중복 여부를 확인
            if (memberService.checkId(memberId)) {
                // 중복된 아이디인 경우
                result = false;
            } 
            else {
                // 중복되지 않은 경우
               result = true;
            }
        }

        // ResponseEntity를 사용하여 결과를 반환
        return ResponseEntity.ok(result);
    }
    
    //임시 로그인 페이지 이동
//	@GetMapping("/login")
//	public String showLogin(Model model) {
//		model.addAttribute("member", new Member());
//		return "login";
//	}
	
	//임시 로그인
//	@PostMapping("/login")
//	public String getLogin(Model model,
//			     @RequestParam("memberId") String memberId,
//			     @RequestParam("memberPw") String memberPw,
//			     HttpSession session) {
//		
//		Member member = memberService.getLogin(memberId, memberPw);
//		
//		if(member != null) {
//			session.setAttribute("loginSession", member);
//			return "redirect:/";
//			
//		} else { 
//			model.addAttribute("msg", "일치하는 회원 정보가 없습니다.");
//			model.addAttribute("member", new Member());
//			return "login";
//		}
//	}
	
	//로그아웃
//	@GetMapping("/logout")
//	public String logout(HttpSession session){
//		session.invalidate();
//		return "redirect:/";
//	}
	
    //회원 정보 수정 이동
    @GetMapping("/MyInfo")
    public String myInfoPage(HttpSession session, Model model) {
    	Login member = (Login) session.getAttribute("loginSession");
		if(member == null) {
			return "redirect:/";
		}
		model.addAttribute("updateProfile", member);
		return "MyInfo";
    }
    
    //회원 정보 수정
    @PostMapping("/updateProfile")
    public String updateMember(HttpSession session, Login updateMember) {
    	Login member = (Login) session.getAttribute("loginSession");
    	
		if(member == null) {
			return "redirect:/";
		}
		
		updateMember.setMemberNo(member.getMemberNo());
		memberService.updateMember(updateMember);
		session.setAttribute("loginSession", updateMember);
		return "redirect:/MyInfo";
    }
    
    //회원 탈퇴
	@GetMapping("/deleteMember")
	public String deleteMember(HttpSession session) {
		Login member = (Login) session.getAttribute("loginSession");		
		if(member == null) {
			return "redirect:/";
		}
		
		//세션에 저장된 member_id 불러오기
		memberService.deleteMember(member.getMemberNo());
		
		session.invalidate(); //삭제 후 로그인 무효화
		return "redirect:/";
	}
	
	//아이디 찾기 페이지 이동
	@GetMapping("/findId")
	public String findIdPage() {
		return "findId";
	}
	
	//아이디 찾기
    @PostMapping("/findId")
    public String findMemberId(@RequestParam("memberName") String memberName,
                               @RequestParam("memberBirth") String memberBirth,
                               Model model) {
        String memberId = memberService.findMemberId(memberBirth, memberName);
        
        if (memberId != null) {
        	model.addAttribute("memberId", memberId);
        	System.out.println(memberId);
            return "findIdResult"; // 결과를 보여줄 HTML 페이지 이름 (예: resultPage.html)
        } else {
        	model.addAttribute("msg", "일치하는 회원 정보가 없습니다.");
            return "findId"; // 회원을 찾지 못했을 경우의 페이지 (예: notFoundPage.html)
        }
    }
    
    //아이디 찾기 결과 페이지 이동
    @GetMapping("/findIdResult")
    public String findIdResultPage() {
    	return "findIdResult";
    }
    
    //비밀번호 찾기 페이지 이동
    @GetMapping("/findPw")
    public String findPwPage() {
    	return "findPw";
    }
    
    //비밀번호 찾기
    @PostMapping("/findPw")
    public String findMemberPw(@RequestParam("memberId") String memberId,
                               @RequestParam("memberEmail") String memberEmail,
                               Model model) {
        String memberPw = memberService.findMemberPw(memberId, memberEmail);
        
        if (memberPw != null) {
            // 비밀번호 마스킹 처리
            String maskedPassword = maskPassword(memberPw);
            
            model.addAttribute("memberPw", maskedPassword);
            System.out.println(maskedPassword); // 마스킹된 비밀번호 출력
            return "findPwResult"; // 비밀번호 찾기 결과 페이지로 이동
        } else {
            model.addAttribute("msg", "일치하는 회원 정보가 없습니다.");
            return "findPw"; // 회원을 찾지 못했을 경우의 페이지로 이동
        }
    }

    // 비밀번호 마스킹 처리 메서드
    private String maskPassword(String password) {
        int length = password.length();
        StringBuilder masked = new StringBuilder();
        
        // 처음 세 자리를 제외하고 나머지는 마스킹 처리
        for (int i = 0; i < length; i++) {
            if (i < 3) {
                masked.append(password.charAt(i));
            } else {
                masked.append('*');
            }
        }
        
        return masked.toString();
    }
    
    //비밀번호 찾기 결과 페이지 이동
    @GetMapping("/findPwResult")
    public String findPwResultPage() {
    	return "findPwResult";
    }
}
