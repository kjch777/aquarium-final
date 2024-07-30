package com.camel.project.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrder {

	private int itemNo; // 상품 번호
	private String itemName; // 상품 이름
	private String itemDesc; // 상품 설명
	private int itemPrice; // 상품 가격
	private String imgFile; // 이미지 파일(파일 이름.확장자) - DB 에 저장되어 있음
	private String finalPath; // 최종 경로(서비스 단에서 합쳐짐) - DB 에 저장되어 있지 않음
	private List<ItemOrder> itemMainList; // itemMain.html 에 전달할 변수

	private int orderQuantity;
	// 주문 수량 - itemDetail.html ▶ orderInput.html 로 넘겨서
	// form action="../orderInfo" method="post" 태그 안의 버튼을 눌러서 제출했을 때,
	// DB 에 저장되게 만들 것

	private int orderNo; // 주문 번호
	private String recName; // 수령자 이름
	private String recPhone; // 수령자 전화번호
	private String recAddr; // 수령자 기본 주소
	private String recDetailAddr; // 수령자 상세 주소
	private String recPostcode; // 수령자 우편 번호
	private String recRequest; // 주문 시 요청 사항
	private String paymentMethod; // 결제 방식
	private String cardType; // 카드 종류
	private String installment; // 할부 선택
	private String bankType; // 은행 선택

	private int totalPrice; // 결제할 총 가격

	private String memberId; // 회원 ID

	// 자료형 Date 의 import 는 주로 java.util 이 사용된다.

}
