package com.sharingdonation.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardCommentDto {

	private Long id;
	
	private Long donationBoardId;
	
	private Long memberId;
	
	private String comment;
	
	private LocalDateTime regTime;
}
