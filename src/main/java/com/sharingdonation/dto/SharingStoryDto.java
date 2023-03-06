package com.sharingdonation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.SharingImg;
import com.sharingdonation.entity.Story;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharingStoryDto {
	private Long sharingId;
	private String sharingName;
	private String imgUrl;
	private String areaName;
	private String createdBy;
	private String regDate;
	private Long numOfStory;
	private String status;
	
	@QueryProjection
	public SharingStoryDto(Sharing sharing, SharingImg sharingImg, Long numOfStory) {
		this.sharingId = sharing.getId();
		this.sharingName = sharing.getName();
		this.imgUrl = sharingImg.getImgUrl();
		this.areaName = sharing.getArea().getGugun();
		this.createdBy = sharing.getMember().getName();
		this.regDate = sharing.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.numOfStory = numOfStory;
		if (sharing.getEndDate() != null) 
			this.status = sharing.getDone().equals("Y") ? "완료" : LocalDate.now().isAfter(sharing.getEndDate()) ? "종료" : "진행중";
	}
}
