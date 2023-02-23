package com.sharingdonation.dto;

import java.time.LocalDateTime;

import com.sharingdonation.entity.Donation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardDto {
	
	public DonationBoardDto(Donation donation) {
		this.donationId = donation.getId();
	}
	
	private Long id;
	
	private Long donationId;
	
	private String subject;
	
	private String content;
	
	private LocalDateTime regTime;
}
