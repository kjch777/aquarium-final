package com.camel.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camel.project.dto.Reserve;
import com.camel.project.mapper.ReserveMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReserveService {
	@Autowired
	private ReserveMapper reserveMapper;
	
	// 예약정보를 DB에 삽입
	public void insertReserve(Reserve reserve) {
		log.info("reserve 값 :" + reserve);
		reserveMapper.insertReserve(reserve);
	}
	
	// 예약정보 조회
	/*
	public List<Reserve> getAllReserve() {
		return reserveMapper.getAllReserve();
	} 
	 * */
	public List<Reserve> getAllReserve(String memberId) {
		return reserveMapper.getAllReserve(memberId);
	}
	
	
	// 예약삭제
	public void deleteReservations(List<Integer> reservNo) {
	    for (Integer no : reservNo) {
	        reserveMapper.deleteReservation(no);
	    }
	}
	
	// 예약정보 업데이트
	/*
	public void updateReserve() {
		reserveMapper.updateReserve();
	}
	*/
	public void updateReserve(String memberId) {
		reserveMapper.updateReserve(memberId);
	}
}
