package com.sharingdonation.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String email;
	
	private String password;
	
	private String cellphone;
	
	private String address;
	
	@NotBlank(message = "닉/사업자명은 필수 입력 값입니다.")
	private String nickName;
	
	private LocalDate birth;
	
	private String comNum;
	
	private String fax;
	
	private int point;
}
