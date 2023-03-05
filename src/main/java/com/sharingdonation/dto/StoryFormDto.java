package com.sharingdonation.dto;

import com.sharingdonation.entity.Story;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoryFormDto {
	private Long id;
	private Long sharingId;
	private Long memberId;
	private String memberName;
	private String content;
	private String registeredYn;
	
	public StoryFormDto(Long sharingId, Long memberId, String registeredYn) {
		this.sharingId = sharingId;
		this.memberId = memberId;
		this.registeredYn = registeredYn;
	}

	public StoryFormDto(Story story) {
		this.id = story.getId();
		this.sharingId = story.getSharing().getId();
		this.memberId = story.getMember().getId();
		this.memberName = story.getMember().getName();
		this.content = story.getContent();
	}
}
