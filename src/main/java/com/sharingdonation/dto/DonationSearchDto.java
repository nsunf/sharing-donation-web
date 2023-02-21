package com.sharingdonation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationSearchDto {
	private String searchBy;
	private String searchQuery = "";
}
