package com.sharingdonation.dto;

import java.time.format.DateTimeFormatter;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.Story;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryDto {
	private Long id;
	
	private Long sharingId;
	
	private Long memberId;

	private String memberName; 
	
	private String content;

	private String regTime;
	
	private String chooseYn;
	
	private String delYn;
	
	@QueryProjection
	public StoryDto(Sharing sharing, Story story, Member member) {
		this.id = story.getId();
		this.sharingId = sharing.getId();
		this.memberId = member.getId();
		this.memberName = member.getName();
		this.content = story.getContent();
		this.regTime = story.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.chooseYn = story.getChooseYn();
		this.delYn = story.getDelYn();
	}
}