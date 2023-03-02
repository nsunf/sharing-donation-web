package com.sharingdonation.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sharingdonation.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageStoryDetailDto {

	private Long id;
	
	private String imgUrl;
	
	private String name;
	
	private String gugun;
	
	private String nickName;
	
	private String detail;
	
	private String content;
	
	//디폴트 생성자
	public MyPageStoryDetailDto() {}

	public MyPageStoryDetailDto(Long id,String detail,String content ,Role role ,String name, String nickName, String imgUrl, String gugun) {
		this.id = id;
		this.imgUrl = imgUrl;
		this.name = name;
		this.gugun = gugun;
		this.nickName = nickName;
		this.detail = detail;
		this.content = content;
		
				
	}
	
	
}
