package com.sharingdonation.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.sharingdonation.entity.Sharing;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharingFormDto {
	
	private Long id;
	
	@NotBlank(message = "지역을 선택해주세요.")
	private String areaId;
	
	@NotBlank(message = "나눔 상품명을 입력해주세요.")
	private String name;
	
	@NotBlank(message = "나눔 상품 상세정보를 입력해주세요")
	private String content;

	public Sharing createSharing() {
		Sharing sharing = new Sharing();
		
//		cateogry
//		member
//		area

		sharing.setName(name);
		sharing.setDetail(content);
		
		sharing.setRegTime(LocalDateTime.now());

		sharing.setCreateBy("test");
		
		sharing.setConfirmYn("N");
		sharing.setDone("N");
		sharing.setDelYn("N");

		return sharing;
	}
}
