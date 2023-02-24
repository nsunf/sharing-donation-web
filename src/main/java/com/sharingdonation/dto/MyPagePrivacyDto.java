package com.sharingdonation.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPagePrivacyDto {

	private Long id;
	
	private String name;
	
	private String email;
	
	private Date birth;
	
	private String nicName;
	
	private String zip_code;
	
	private String address;
	
	private String address_detail;
	
	private Date reg_time;


	public MyPagePrivacyDto(Long id, String name, String email,Date birth, String nicName, String zip_code, String address, String address_detail, Date reg_time) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.birth = birth;
		this.nicName = nicName;
		this.zip_code = zip_code;
		this.address = address;
		this.address_detail = address_detail;
		this.reg_time = reg_time;
				
				
		
	}
	
	
}
