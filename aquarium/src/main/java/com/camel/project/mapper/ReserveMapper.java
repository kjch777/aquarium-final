package com.camel.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camel.project.dto.Reserve;

import lombok.extern.slf4j.Slf4j;

@Mapper
public interface ReserveMapper {
	// 예약정보 DB에 삽입
	void insertReserve(Reserve reserve);
	
	// 예약이 여러개 있을 수도, 한개만 있을수도, 없을수도 있음 -> 회원 예약정보를 Reserve에서 가져와 리스트로 담기 
	//List<Reserve> getAllReserve();
	List<Reserve> getAllReserve(@Param("memberId") String memberId);
	
	
	// 예약삭제
	void deleteReservation(@Param("reservNo") Integer reservNo);
	
	
	// 예약만료여부값 업데이트
	//void updateReserve();
	void updateReserve(@Param("memberId") String memberId);
	
}
