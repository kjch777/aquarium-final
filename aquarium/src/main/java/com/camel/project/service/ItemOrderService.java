package com.camel.project.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camel.project.dto.ItemOrder;
import com.camel.project.mapper.ItemOrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemOrderService {

	// 고정 경로 - DB 에 저장된 값이 아니다.
	public static final String FIXEDPATH = "../img/";

	@Autowired
	private ItemOrderMapper itemOrderMapper;

	public List<ItemOrder> getAllItem(){
		List<ItemOrder> itemList = null; // itemList 초기화
		itemList = itemOrderMapper.getAllItem();

		/*
		 * 여기서 이미지 파일 경로 합성을 진행한다.
		 * DB 에는 이미지 파일 이름.확장자 까지만 저장을 하고, 여기에서 고정 경로와 합성하여 완전한 경로를 만들어낸다.
		 * */
		for(ItemOrder i : itemList) {
			i.setFinalPath(FIXEDPATH + i.getImgFile());
		}

		return itemList;
	}

	public ItemOrder getItemByNo(int itemNo) {
		ItemOrder itemOrder = new ItemOrder();

		itemOrder = itemOrderMapper.getItemByNo(itemNo);

		itemOrder.setFinalPath(FIXEDPATH + itemOrder.getImgFile());

		List<ItemOrder> itemList = itemOrderMapper.getAllItem();

		// itemDetail.html 화면의 하단에, 화면의 상단에 보여지는 상품을 제외한 나머지가 랜덤하게 보여지게 만들기
		itemList.remove(itemNo-1);
		Collections.shuffle(itemList);
		List<ItemOrder> randomItemList = itemList.subList(0, Math.min(3, itemList.size()));

		/*
		 * 여기서 이미지 파일 경로 합성을 진행한다.
		 * DB 에는 이미지 파일 이름.확장자 까지만 저장을 하고, 여기에서 고정 경로와 합성하여 완전한 경로를 만들어낸다.
		 * */
		for(ItemOrder i : randomItemList) {
			i.setFinalPath(FIXEDPATH + i.getImgFile());
		}

		itemOrder.setItemMainList(itemList);

		return itemOrder;
	}

	public ItemOrder getOrderByNo(ItemOrder param) {
		ItemOrder itemOrder = new ItemOrder();
		itemOrder = itemOrderMapper.getOrderByNo(param);

		return itemOrder;
	}

	public void insertOrder(ItemOrder itemOrder) {
		itemOrderMapper.insertOrder(itemOrder);
	}

	public List<ItemOrder> getAllOrder(String memberId){
		return itemOrderMapper.getAllOrder(memberId);
	}

	public void deleteOrder(int orderNo) {
		itemOrderMapper.deleteOrder(orderNo);
	}

	public String getOrderMemberId(int orderNo){
		String memberId = null;
		System.out.println("service시작합니다.");
		memberId = itemOrderMapper.getOrderMemberId(orderNo);
		System.out.println("memberId 입니다. : " + memberId);

		return memberId;
		//return itemOrderMapper.getOrderMeberId(orderNo);
	}
}
