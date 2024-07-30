package com.camel.project.dto;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Reserve {
	private int reservNo;
	private String memberId;
	private String programNo;
	private int adultCount;
	private int childCount;
	private int reservTotalPrice;
	//private String reservTotalPrice;
	private Date paymentDate;
	private Date reservDate;
	private String reservCancle;
	private String reservExprt;
	
	private String programName;

}
