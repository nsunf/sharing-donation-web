package com.sharingdonation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardSearchDto {
	private String searchBy;
	private String searchQuery = "";
}
