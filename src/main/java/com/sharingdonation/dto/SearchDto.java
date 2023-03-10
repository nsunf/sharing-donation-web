package com.sharingdonation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
	//subject | donationName |author
	private String searchBy = "subject";
	// all | outstanding | proceeding | complete
	private String status = "all";
		
	private String searchQuery = "";
}
