package com.sharingdonation.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	private String email;
	
	private String password;
	
	private String cellphone;
	
	private String address;
	
	@NotBlank(message = "닉/사업자명은 필수 입력 값입니다.")
	private String nickName;
	
	private String birth;
	
	private String comNum;
	
	private String fax;
	
	private int point;
}
