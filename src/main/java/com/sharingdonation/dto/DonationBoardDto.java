package com.sharingdonation.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardDto {
	
	private Long id;
	
	private Long donationId;
	
	private String subject;
	
	private String content;
	
	private LocalDateTime regTime;
	
	private String imgUrl;
	
	@QueryProjection
	public DonationBoardDto(Long id, Long donationId, String subject, String content,  LocalDateTime regTime, String imgUrl) {
		this.id = id;
		this.donationId = donationId;
		this.subject = subject;
		this.content = content;
		this.regTime = regTime;
		this.imgUrl = imgUrl;
		
	}
}
