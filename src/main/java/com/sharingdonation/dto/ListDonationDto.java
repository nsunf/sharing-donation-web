package com.sharingdonation.dto;

import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDonationDto {
	private Long id;
	
	private String donatinName;
	
	private String imgUrl;
	
	private String subject;
	
//	private Member nickName; // todo member
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private Integer goalPoint;
	
	@QueryProjection
	public ListDonationDto(Long id, String donationName,  LocalDate startDate, LocalDate endDate, Integer goalPoint) { // Member nickName,
		this.id = id;
		this.donatinName = donationName;
//		this.nickName = nickName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.goalPoint = goalPoint;
	}
}
