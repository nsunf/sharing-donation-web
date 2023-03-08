package com.sharingdonation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.constant.Role;
import com.sharingdonation.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberAllDto {
	private Long id;

	private String email;
	
	private String name;
	
	private String cellphone;
	
	private String zipCode;
	
	private String address;
	
	private String addressDetail;
	
	private String nickName;
	
	private LocalDate birth;
	
	private String comNum;
	
	private String fax;
	
	private int point;

	private Role role;
	
	private String delYn;
	
	private LocalDateTime regTime;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static MemberAllDto of(Member member) {
		return modelMapper.map(member, MemberAllDto.class);
	}
	
//	@QueryProjection
//	public MemberAllDto(Long id, String name,  String nickName, LocalDateTime regTime, Role role) {
//		this.id = id;
//		this.name = name;
//		this.nickName = nickName;
//		this.regTime = regTime;
//		this.role = role;
//	}
}
