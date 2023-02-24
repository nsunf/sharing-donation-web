package com.sharingdonation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sharingdonation.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationDto {
	
	public DonationDto(Member member) {
		this.memberId = member.getId();
		this.nickName = member.getNickName();
		
	}
	private Long id;
	
	private String donationName;
	
	private Long memberId;
	
	private String nickName;
	
	private String donationPerson;
	
	private String donationTel;
	
	private String subject;
	
	private String detail;
	
	private int price;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String address;
	
	private int goalPoint;
	
	private LocalDateTime regTime;
	
	private String confirmYn;
	
	private String done;
	
	private String delYn;
	
}
