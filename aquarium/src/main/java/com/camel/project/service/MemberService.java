package com.camel.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.camel.project.dto.Login;
import com.camel.project.dto.Member;
import com.camel.project.mapper.MemberMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	//회원 정보 넣기
	@Autowired
	private MemberMapper memberMapper;
	
	public void insertMember(Member member) {
		memberMapper.insertMember(member);
	}
	
	//이메일 인증
	private final JavaMailSender JavaMailSender;
	private static final String senderEmail = "qwert850528@gmail.com";
	private static int number;
	
	public static void makingNumber() {
		number = (int)(Math.random()*(1000)) + 9000;
	}
	
	public MimeMessage createMail(String mail) {
		makingNumber();
		MimeMessage message = JavaMailSender.createMimeMessage();
		
		try {
			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, mail);
			message.setSubject("이메일 인증");
			String content = "";
			content += "<h3>" + "다음 인증번호를 입력해주세요." + "<h3>";
			content += "<h1>" + number + "<h1>";
			message.setText(content, "utf-8", "html");
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}
	
	public int sendMail(String mail) {
		MimeMessage message = createMail(mail);
		JavaMailSender.send(message);
		return number;
	}
	
	//아이디 중복 검사
    public boolean checkId(String memberId) {
        return memberMapper.countById(memberId) > 0;
    }
    
    //임시 로그인
	public Member getLogin(String memberId, String memberPw) {
		return memberMapper.getLogin(memberId, memberPw);
	}
    	
	//회원정보 수정
	public void updateMember(Login login) {
		
		memberMapper.updateMember(login);
	}
	
	//회원 탈퇴
	public void deleteMember(int memberNo) {
		
		memberMapper.deleteMember(memberNo);
	}
	
	//아이디 찾기
    public String findMemberId(String memberBirth, String memberName) {
        Member member = memberMapper.findByBirthAndName(memberBirth, memberName);
        
        if (member != null) {
            return member.getMemberId();
        } else {
            return null; // 회원을 찾지 못했을 경우
        }
    }
    
	//비밀번호 찾기
    public String findMemberPw(String memberId, String memberEmail) {
        Member member = memberMapper.findByEmailAndId(memberId, memberEmail);
        
        if (member != null) {
            return member.getMemberPw();
        } else {
            return null; // 회원을 찾지 못했을 경우
        }
    }
	
}
