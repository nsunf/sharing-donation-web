package com.sharingdonation.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorpFormDto {
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
//	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
//            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
	private String password;
	
	@NotEmpty(message = "휴대폰번호는 필수 입력 값입니다.")
	private String cellphone;
	
	@NotEmpty(message = "우편번호는 필수 입력 값입니다.")
	private String zipCode;
	
	@NotEmpty(message = "주소는 필수 입력 값입니다.")
	private String address;
	
	@NotEmpty(message = "주소는 필수 입력 값입니다.")
	private String addressDetail;
	
	@NotBlank(message = "사업자명은 필수 입력 값입니다.")
	private String nickName;
	
	private String birth;
	
	@NotEmpty(message = "사업자번호는 필수 입력 값입니다.")
	private String comNum;
	
	@NotEmpty(message = "FAX번호는 필수 입력 값입니다.")
	private String fax;
	

}