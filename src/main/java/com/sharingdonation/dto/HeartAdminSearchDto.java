package com.sharingdonation.dto;

import com.sharingdonation.constant.HeartType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartAdminSearchDto {
	// title | author
	private String filter = "title";
	
	private HeartType type = HeartType.SHARING;

	private String search = "";
}

