package com.camel.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camel.project.dto.Login;
import com.camel.project.dto.Member;
import com.camel.project.dto.Reserve;
import com.camel.project.service.ReserveService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ReserveController {
	
	// program.html로 이동
	@GetMapping("/program")
	public String program() {
		return"program";
	}
	
	// information.html로 이동
	@GetMapping("/information")
	public String usageguide() {
		return "information";
	}
	
	// reservConfirm.html로 이동
	@GetMapping("/reserveConfirm")
	public String reserveConfirm() {
		return "reservConfirm";
	}

	// reserv.html로 이동
	@GetMapping("/reserve")
	public String reserveForm(String program, Model model) {
		// DB에 값이 들어갈 수 있도록 빈공간 만들어주기
		model.addAttribute("program", program); // url 파라미터로 받은 프로그램 정보 전달 
		model.addAttribute("reserve", new Reserve());
		return "reserv";// reserv.html 파일을 가리킴 
	}
	
	@Autowired
	private ReserveService reserveService;
	
	@PostMapping("/reserve")
	public String insertReserve(Reserve reserve, Model model, HttpSession session) {
		
		//로그인 안되어있을 시 로그인 화면으로 리틴 시키는 코드 추가
		// Member member = (Member) session.getAttribute("loginSession"); 
		Login login = (Login) session.getAttribute("loginSession");
		// Member -> member DB테이블과 연결된 DTO자바파일명
		// loginSession -> 로그인 했을 때 session 명(?) -> session.세션명
		
		if (login == null) {
			return "redirect:/login";
		}
		
		//reserve.setMemberId(member.getMember_id()); // 로그인한 아이디값을 Member DTO에서 가져와서 넣어주기
		reserve.setMemberId(login.getMemberId());
		//reserve.setMemberId("reserveId"); // 임시
		reserve.setReservCancle("N");
        reserve.setReservExprt("N");
        log.info("reserve 값 :" + reserve);
		reserveService.insertReserve(reserve);
		return "reservConfirm"; 
		//return "redirect:/reservConfirm"; //예약 완료 페이지로 이동
		
	}
	
	// 예약정보업데이트
	@PostMapping("/updateAndShowReservations")
	public String updateAndShowReservations(Model model, HttpSession session) {
		/*
		로그인 안되어있을 시 로그인 화면으로 리틴 시키는 코드 추가 
		*/
        // 예약 정보를 업데이트
		
		// Member member = (Member) session.getAttribute("loginSession"); 
		Login login = (Login) session.getAttribute("loginSession");
		
		if (login == null) {
			return "redirect:/login";
		}
		
		// 예약 정보를 업데이트
		String memberId = login.getMemberId();
        reserveService.updateReserve(memberId);

        // 업데이트 후 예약 정보를 조회하여 모델에 추가
        List<Reserve> reserve = reserveService.getAllReserve(memberId);
        //model.addAttribute("reservList", reserve);
        return "redirect:/reserveConfirmCheck";
    }
	
	
	@GetMapping("/reserveConfirmCheck")
	public String getAllReserve(Model model, HttpSession session) {
		// Member member = (Member) session.getAttribute("loginSession"); 
		Login login = (Login) session.getAttribute("loginSession");
		
		if (login == null) {
			return "redirect:/login";
		}
		
		String memberId = login.getMemberId();
        reserveService.updateReserve(memberId);
		
		List<Reserve> reserve = reserveService.getAllReserve(memberId);
		log.info("DB에 저장된 reserve 모두 보기 : " + reserve);
		model.addAttribute("reservList", reserve);
		return "reservConfirm";
	}
	
	// 예약삭제
	@PostMapping("/deleteReservation")
	public String deleteReservation(@RequestParam List<Integer> reservNo, 
									HttpSession session) {
		/*
		로그인 안되어있을 시 로그인 화면으로 리틴 시키는 코드 추가 
		*/
		// Member member = (Member) session.getAttribute("loginSession"); 
		Login login = (Login) session.getAttribute("loginSession");
		
		if (login == null) {
			return "redirect:/login";
		}
		
		
		log.info("가져온 reservNo 리스트 : " + reservNo);
	    reserveService.deleteReservations(reservNo);
	    return "redirect:/reserveConfirmCheck"; // 확인 페이지로
	    //return "redirect:/reserveConfirm"; // 다시 예약 확인 페이지로 리다이렉트
	}

}
