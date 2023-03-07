package com.sharingdonation.dto;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageMainDto {

	private Long id;
	
	private int point;
	
	private Long share_reg;
	
	private Long share_take;
	
	private Long share_apply;
	
	private Long share_story;
	
	private String name;
	 
	private LocalDateTime regTime;
	
	private String nickName;
	
	private Role role;
	
	private String regTimeStr;
	
 
	
	//디폴트 생성자 
	public MyPageMainDto() {}

	public MyPageMainDto(Long id,String nickName,int point, Long share_reg, Long share_take, Long share_apply, Long share_story, String name, LocalDateTime regTime, Role role) {
		this.id = id;
		this.point = point;
		this.share_reg = share_reg;
		this.share_take = share_take;
		this.share_apply = share_apply;
		this.share_story = share_story;
		this.name = name;
		this.regTime = regTime;
		this.nickName = nickName;
		this.role = role;
		this.regTimeStr = regTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	}
 
	
		
 
}
