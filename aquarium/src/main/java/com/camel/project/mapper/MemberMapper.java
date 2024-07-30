package com.camel.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.camel.project.dto.Login;
import com.camel.project.dto.Member;

@Mapper
public interface MemberMapper {

	// 회원 정보 삽입
	public void insertMember(Member member);

	// 아이디 중복 검사
	public int countById(@Param("memberId") String memberId);

	// 임시 로그인
	Member getLogin(@Param("memberId") String memberId, @Param("memberPw") String memberPw);

	// 회원 정보 수정
	void updateMember(Login login);

	// 회원 탈퇴
	void deleteMember(@Param("memberNo") int memberNo);

	// 아이디 찾기
	Member findByBirthAndName(@Param("memberBirth") String memberBirth, @Param("memberName") String memberName);

	// 비밀번호 찾기
	Member findByEmailAndId(@Param("memberId") String memberId, @Param("memberEmail") String memberEmail);
}
