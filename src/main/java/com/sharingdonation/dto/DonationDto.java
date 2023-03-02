package com.sharingdonation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationDto {
	
//	public DonationDto(Member member) {
//		this.memberId = member.getId();
//		this.nickName = member.getNickName();
//		
//	}
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
	
	private String imgUrl;
	
	private Long heartCount;
	
	private Integer pointSum;
	
	@QueryProjection
	public DonationDto(Long id, String subject
			, String donationName
			, LocalDate startDate
			, LocalDate endDate, int goalPoint, String imgUrl
			, String nickName
			, String confirmYn
			, String delYn
			, String done
			, LocalDateTime regTime
			, Long heartCount
			, Integer pointSum
			
//			, Integer pointPer
			) { 
		// Member nickName, , String confirmYn, String done, String delYn  String nickName,
		// , Long heartCount
		this.id = id;
		this.subject = subject;
		this.donationName = donationName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.goalPoint = goalPoint;
		this.imgUrl = imgUrl;
		this.nickName = nickName;
		this.confirmYn = confirmYn;
		this.delYn = delYn;
		this.done = done;
		this.regTime = regTime;
		this.heartCount = heartCount;
		this.pointSum = pointSum;
//		this.pointPer = pointPer;
	}
	
}
