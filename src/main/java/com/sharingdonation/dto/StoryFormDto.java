package com.sharingdonation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoryFormDto {
	private Long sharingId;
	private Long memberId;
	private String content;
	private String registeredYn;
	
	public StoryFormDto(Long sharingId, Long memberId, String registeredYn) {
		this.sharingId = sharingId;
		this.memberId = memberId;
		this.registeredYn = registeredYn;
	}
}
