package com.sharingdonation.dto;

import java.time.format.DateTimeFormatter;

import com.sharingdonation.entity.Sharing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
