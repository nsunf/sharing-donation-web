package com.sharingdonation.dto;

import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDonationDto {
	
	public ListDonationDto(Member member) {
		this.nickName = member.getNickName();
	}
	private Long id;
	
	private String imgUrl;
	
	private String subject;
	
	private String nickName; // todo member
	
//	private int price;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private Integer goalPoint;
	
//	private String confirmYn;
//	
//	private String done;
//	
//	private String delYn;
	
//	private String donationPerson;
//	
//	private String donationTel;
	
	@QueryProjection
	public ListDonationDto(Long id, String subject, String nickName, LocalDate startDate, LocalDate endDate, Integer goalPoint) { 
		// Member nickName, , String confirmYn, String done, String delYn
		this.id = id;
		this.subject = subject;
		this.nickName = nickName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.goalPoint = goalPoint;
	}
}
