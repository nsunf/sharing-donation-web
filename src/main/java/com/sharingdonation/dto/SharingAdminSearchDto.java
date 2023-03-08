package com.sharingdonation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharingAdminSearchDto {
	// title | content | author
	private String filter = "title";

	// all | outstanding | proceeding | complete
	private String status = "all";

	private String search = "";
}
