package com.camel.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camel.project.dto.ItemOrder;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemOrderMapper {

	List<ItemOrder> getAllItem();

	ItemOrder getItemByNo(int itemNo);

	ItemOrder getOrderByNo(ItemOrder param);

	void insertOrder(ItemOrder itemOrder);

	List<ItemOrder> getAllOrder(@Param("memberId") String memberId);

	void deleteOrder(@Param("orderNo") int orderNo);

	String getOrderMemberId(int orderNo);
}
