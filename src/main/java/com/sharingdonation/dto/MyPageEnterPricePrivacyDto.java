package com.sharingdonation.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sharingdonation.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageEnterPricePrivacyDto {

	private Long id;
	
	private String name;
	
	private String email;
	
	private String cellphone;
	
	private String comNum;
	
	private String fax;
	
	private String nickName;
	
	private String zipCode;
	
	private String address;
	
	private String addressDetail;
	
	private LocalDateTime regTime;
	
	private Role role;

	//디폴트 생성자
	public MyPageEnterPricePrivacyDto() {}

	public MyPageEnterPricePrivacyDto(Long id,Role role ,String cellphone, String comNum, String fax  , String name, String email,  String nickName, String zipCode, String address, String addressDetail, LocalDateTime regTime) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cellphone = cellphone;
		this.comNum = comNum;
		this.fax = fax;
		this.nickName = nickName;
		this.zipCode = zipCode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.regTime = regTime;
		this.role = role;
				
	}
	
	
}
