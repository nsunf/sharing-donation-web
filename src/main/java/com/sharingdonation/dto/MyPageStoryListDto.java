package com.sharingdonation.dto;

import java.time.LocalDateTime;

import com.sharingdonation.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageStoryListDto {
	
	private Long id;
	
	private String name;
	
	private String imgUrl;
	
	private Role role;
	
	private String nickName;
	
	private Long sharingId;
	
	private String sharProductName;
	
	private LocalDateTime regTime;
	
	private String content;
	
	
	
	public MyPageStoryListDto() {};
	
	public MyPageStoryListDto(Long id, String name, String imgUrl,LocalDateTime regTime,Role role,
			String nickName,Long sharingId,String sharProductName,String content) {
		this.id = id;
		this.name = name;
		this.imgUrl = imgUrl;
		this.regTime = regTime;
		this.role = role;
		this.nickName = nickName;
		this.sharingId = sharingId;
		this.sharProductName = sharProductName;
		this.content = content;

	};
	
}
