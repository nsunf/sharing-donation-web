package com.sharingdonation.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberChangePwsDto {
	
	private String email;
	
	private String name;
	
	private String cellphone;
	
	private String password;
	
	public MemberChangePwsDto(String email, String name, String cellphone, String password) {
		this.email = email;
		this.name = name;
		this.cellphone = cellphone;
		this.password = password;
	}
}


