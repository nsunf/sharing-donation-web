package com.sharingdonation.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sharingdonation.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPagePrivacyDto {

	private Long id;
	
	private String name;
	
	private String email;
	
	private LocalDate birth;
	
	private String nickName;
	
	private String zipCode;
	
	private String address;
	
	private String addressDetail;
	
	private LocalDateTime regTime;

	private String regTimeStr;
	
	private Role role;

	//디폴트 생성자
	public MyPagePrivacyDto() {}

	public MyPagePrivacyDto(Long id,Role role ,String name, String email,LocalDate birth, String nickName, String zipCode, String address, String addressDetail, LocalDateTime regTime) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.birth = birth;
		this.nickName = nickName;
		this.zipCode = zipCode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.regTime = regTime;
		this.regTimeStr = regTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.role = role;
				
	}
	
	
}
