package com.sharingdonation.dto;

import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDonationDto {
	
//	public ListDonationDto(Member member) {
//		this.nickName = member.getNickName();
//	}
	
	private Long id;
	
	private String subject;
	
	private String nickName; // todo member
	
//	private int price;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private int goalPoint;
	
	private String imgUrl;
	
	private Long heartCount;
//	private String confirmYn;
//	
//	private String done;
//	
//	private String delYn;
	
//	private String donationPerson;
//	
//	private String donationTel;
	
	@QueryProjection
	public ListDonationDto(Long id, String subject, LocalDate startDate
			, LocalDate endDate, int goalPoint, String imgUrl
			, String nickName
			, Long heartCount
			) { 
		// Member nickName, , String confirmYn, String done, String delYn  String nickName,
		// , Long heartCount
		this.id = id;
		this.subject = subject;
		this.startDate = startDate;
		this.endDate = endDate;
		this.goalPoint = goalPoint;
		this.imgUrl = imgUrl;
		this.nickName = nickName;
		this.heartCount = heartCount;
	}
}
