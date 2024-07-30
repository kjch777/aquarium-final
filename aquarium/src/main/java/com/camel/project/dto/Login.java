package com.camel.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private int memberNo;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private String memberName;
    private String memberBirth;
    private String memberPhone;
    private String memberAddress;
    private String memberSignUpDate;
}