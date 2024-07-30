package com.camel.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camel.project.dto.ItemOrder;
import com.camel.project.dto.Login;
import com.camel.project.dto.Member;
import com.camel.project.service.ItemOrderService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ItemOrderController {

	@Autowired
	private ItemOrderService itemOrderService;

	@GetMapping("/itemMain")
	public String getAllItem(Model model) {
		List<ItemOrder> item = itemOrderService.getAllItem();
		model.addAttribute("item", item);

		return "itemMain";
	}

	@GetMapping("/itemDetail/{itemNo}")
	public String getItemByNo(Model model, @PathVariable int itemNo) {
		ItemOrder itemOrder = itemOrderService.getItemByNo(itemNo);
		model.addAttribute("itemD", itemOrder);

		return "itemDetail";
	}

	@PostMapping("/orderInput")
	public String getOrderByNo(@RequestParam("orderQuantity") int orderQuantity, @RequestParam("totalPriceValue") int totalPrice, ItemOrder param, Model model, HttpSession session) { // HttpSession session 추가함
		
		//로그인 안되어있을 시 로그인 화면으로 리틴 시키는 코드 추가
		// Member member = (Member) session.getAttribute("loginSession");
		Login login = (Login) session.getAttribute("loginSession");
		// Member -> member DB테이블과 연결된 DTO자바파일명
		// loginSession -> 로그인 했을 때 session 명(?) -> session.세션명
		if (login == null) {
			return "redirect:/login";
		}
		// 여기까지 추가했음
		
		ItemOrder setQuantity = new ItemOrder();
		setQuantity.setOrderQuantity(orderQuantity);

		ItemOrder setPrice = new ItemOrder();
		setPrice.setTotalPrice(totalPrice);

		param.setTotalPrice(totalPrice);

		ItemOrder loadName = itemOrderService.getOrderByNo(param);

		model.addAttribute("loadName", loadName);
		model.addAttribute("setQuantity", setQuantity);
		model.addAttribute("setTotalPrice", setPrice);
		model.addAttribute("order", param);

		return "orderInput";
	}

	@GetMapping("/orderInput")
	public String orderInputForm(Model model) {
		model.addAttribute("order", new ItemOrder());

		return "orderInput";
	}

	@PostMapping("/orderInfo")
	public String insertOrder(ItemOrder itemOrder, Model model, HttpSession session) { // HttpSession session 추가함
		
		//로그인 안되어있을 시 로그인 화면으로 리틴 시키는 코드 추가
// 		Member member = (Member) session.getAttribute("loginSession");
		Login login = (Login) session.getAttribute("loginSession");
		// Member -> member DB테이블과 연결된 DTO자바파일명
		// loginSession -> 로그인 했을 때 session 명(?) -> session.세션명
		itemOrder.setMemberId(login.getMemberId());
		
		itemOrderService.insertOrder(itemOrder);

		// 회원 ID로 주문 정보 조회
		List<ItemOrder> orderList = itemOrderService.getAllOrder(itemOrder.getMemberId());
		model.addAttribute("order", orderList);

		return "orderInfo";
	}

	@PostMapping("/cancelOrder")
	public String cancelOrder(@RequestParam("orderNos") Integer[] orderNos, Model model) {

		//orderNo에 해당하는 memberid를 먼저 찾자
		String memberId = null;
		List<ItemOrder> orderList = null;

		for (int orderNo : orderNos) {
			System.out.println("컨트롤러 시작합니다.");
			memberId = itemOrderService.getOrderMemberId(orderNo);
			itemOrderService.deleteOrder(orderNo);
		}

		System.out.println("memberId : " + memberId );

		orderList = itemOrderService.getAllOrder(memberId);

		System.out.println("orderList : " + orderList);

//		return "redirect:/orderInfo"; // 취소 후 orderInfo 페이지로 리다이렉트

		// orderList와 memberId를 모델에 추가
		model.addAttribute("order", orderList);
		//model.addAttribute("memberId", memberId);

		return "orderInfo";
	}

	// 수정해야 한다.
	@GetMapping("/orderInfo") // 주석 풀었음
	public String getAllOrder(ItemOrder dto , Model model, HttpSession session) { // HttpSession session 적었음
				
				//로그인 안되어있을 시 로그인 화면으로 리틴 시키는 코드 추가
				// Member member = (Member) session.getAttribute("loginSession");
				Login login = (Login) session.getAttribute("loginSession");
				// Member -> member DB테이블과 연결된 DTO자바파일명
				// loginSession -> 로그인 했을 때 session 명(?) -> session.세션명
				if (login == null) {
					return "redirect:/login";
				}
				// 여기까지 추가했음
				
		List<ItemOrder> orderList = null;
		
		orderList = itemOrderService.getAllOrder(login.getMemberId());

		model.addAttribute("order", orderList);

		return "orderInfo";
	}

}
