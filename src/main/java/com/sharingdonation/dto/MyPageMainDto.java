package com.sharingdonation.dto;

import java.sql.Date;
import java.time.LocalDateTime;

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
	
	private Date reg_time;
	
	
	//디폴트 생성자 
	public MyPageMainDto() {}

	public MyPageMainDto(Long id, int point, Long share_reg, Long share_take, Long share_apply, Long share_story, String name, Date reg_time) {
		this.id = id;
		this.point = point;
		this.share_reg = share_reg;
		this.share_take = share_take;
		this.share_apply = share_apply;
		this.share_story = share_story;
		this.name = name;
		this.reg_time = reg_time;
	}
	
}
