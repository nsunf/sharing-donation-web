package com.sharingdonation.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sharingdonation.entity.Area;
import com.sharingdonation.entity.Category;
import com.sharingdonation.entity.Sharing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SharingFormDto {
	
	private Long id;
	
	private Long memberId;
	
	@NotNull(message = "지역을 선택해주세요.")
	private Long areaId;

	@NotNull(message = "카테고리를 선택해주세요.")
	private Long categoryId;
	
	@NotBlank(message = "나눔 상품명을 입력해주세요.")
	private String name;
	
	@NotBlank(message = "나눔 상품 상세정보를 입력해주세요")
	private String content;
	
	private String regTime;
	
	private String confirmYn;
	
	private String done;
	
	private String delYn;
	
	private String status;

	public Sharing createSharing(Category category, Area area) {
		Sharing sharing = new Sharing();
		sharing.setCategory(category);
		sharing.setArea(area);

		sharing.setName(name);
		sharing.setDetail(content);
		
		sharing.setRegTime(LocalDateTime.now());

//		sharing.setCreateBy("test");
		
		sharing.setConfirmYn("N");
		sharing.setDone("N");
		sharing.setDelYn("N");

		return sharing;
	}
	
	public SharingFormDto(Sharing sharing) {
		this.id = sharing.getId();
		this.memberId = sharing.getMember().getId();
		this.areaId = sharing.getArea().getId();
		this.categoryId = sharing.getCategory().getId();
		this.name = sharing.getName();
		this.content = sharing.getDetail();
		this.regTime = sharing.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		this.confirmYn = sharing.getConfirmYn();
		this.done = sharing.getDone();
		this.delYn = sharing.getDelYn();

		this.status = sharing.getDone().equals("Y") ? "완료" : (sharing.getConfirmYn().equals("Y") ? "진행중": "승인대기");
	}
}
