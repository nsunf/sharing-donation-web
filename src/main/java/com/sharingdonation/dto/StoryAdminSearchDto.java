package com.sharingdonation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryAdminSearchDto {
	// title | content | author
	private String filter = "title";

	// all | proceeding | complete
	private String status = "all";

	private String search = "";
}
