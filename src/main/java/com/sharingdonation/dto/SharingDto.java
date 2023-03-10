package com.sharingdonation.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.SharingImg;
import com.sharingdonation.entity.Story;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SharingDto {
	private Long id;

	private String name;
	
	private String content;

	private String categoryName;
	
	private String areaName;
	
	private String authorEmail;

	private String authorName;
	
	private String regTime;
	
	private String confirmYn;
	
	private String done;
	
	private String status;
	
	private String imgUrl;
	
	private String startDate; 
	
	private String endDate;
	
	private Long heartCount;
	
	private int point;
	

	@QueryProjection
	public SharingDto(Sharing sharing, SharingImg sharingImg, Story story) {
		this.id = sharing.getId();
		this.name = sharing.getName();
		this.content = sharing.getDetail();
		this.categoryName = sharing.getCategory().getCategoryName();
		this.areaName = sharing.getArea().getGugun();
		this.authorEmail = sharing.getMember().getEmail();
		this.authorName = sharing.getMember().getName();
		this.regTime = sharing.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.confirmYn = sharing.getConfirmYn();
		this.done = sharing.getDone();
		this.status = sharing.getDone().equals("Y") ? "완료" : (sharing.getConfirmYn().equals("Y") ? "진행중": "승인대기");
		this.imgUrl = sharingImg.getImgUrl();
		this.startDate = sharing.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.endDate = sharing.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	@QueryProjection
	public SharingDto(Sharing sharing) {
		this.id = sharing.getId();
		this.name = sharing.getName();
		this.content = sharing.getDetail();
		this.categoryName = sharing.getCategory().getCategoryName();
		this.areaName = sharing.getArea().getGugun();
		this.authorEmail = sharing.getMember().getEmail();
		this.authorName = sharing.getMember().getName();
		this.regTime = sharing.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.confirmYn = sharing.getConfirmYn();
		this.done = sharing.getDone();
		this.status = sharing.getDone().equals("Y") ? "완료" : (sharing.getConfirmYn().equals("Y") ? "진행중": "승인대기");
		if (sharing.getStartDate() != null)
			this.startDate = sharing.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (sharing.getEndDate() != null)
			this.endDate = sharing.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static SharingDto valueOf(Sharing sharing, String imgUrl) {
		String _status = sharing.getDone().equals("Y") ? "완료" : (sharing.getConfirmYn().equals("Y") ? "진행중": "승인대기");

		return SharingDto.builder()
				.id(sharing.getId())
				.name(sharing.getName())
				.categoryName(sharing.getCategory().getCategoryName())
				.regTime(sharing.getRegTime().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))
				.confirmYn(sharing.getConfirmYn())
				.done(sharing.getDone())
				.imgUrl(imgUrl)
				.status(_status)
				.point(sharing.getPoint())
				.build();
	}
	
	public static SharingDto valueOf(Sharing sharing) {
		String _status = sharing.getDone().equals("Y") ? "완료" : (sharing.getConfirmYn().equals("Y") ? "진행중": "승인대기");
		String _startDate = null;
		String _endDate = null;
		if (sharing.getStartDate() != null) _startDate = sharing.getStartDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
		if (sharing.getEndDate() != null) _endDate = sharing.getEndDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));

		return SharingDto.builder()
				.id(sharing.getId())
				.name(sharing.getName())
				.content(sharing.getDetail())
				.categoryName(sharing.getCategory().getCategoryName())
				.areaName(sharing.getArea().getGugun())
				.authorEmail(sharing.getMember().getEmail())
				.authorName(sharing.getMember().getName())
				.regTime(sharing.getRegTime().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))
				.confirmYn(sharing.getConfirmYn())
				.done(sharing.getDone())
				.status(_status)
				.point(sharing.getPoint())
				.startDate(_startDate)
				.endDate(_endDate)
				.build();
	}
}
