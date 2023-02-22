package com.sharingdonation.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
	private String password;
	
	@NotEmpty(message = "휴대폰번호는 필수 입력 값입니다.")
	private String cellphone;
	
	@NotEmpty(message = "주소는 필수 입력 값입니다.")
	private String address;
	
	@NotBlank(message = "닉/사업자명은 필수 입력 값입니다.")
	private String nickName;
	
	private String birth;
	
	@NotEmpty(message = "사업자번호는 필수 입력 값입니다.")
	private String comNum;
	
	@NotEmpty(message = "FAX번호는 필수 입력 값입니다.")
	private String fax;
	
	private int point;
}
