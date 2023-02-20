package com.sharingdonation.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sharingdonation.constant.Role;
import com.sharingdonation.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String name;
	
	private String cellphone;
	
	private String address;
	
	private String nickName;
	
	private String birth;
	
	private String comNum;
	
	private String fax;
	
	private int point;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private LocalDateTime regTime;
	
	private String delYn;
	
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setAddress(memberFormDto.getAddress());
		member.setEmail(memberFormDto.getEmail());
		member.setBirth(memberFormDto.getBirth());
		
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		
		member.setRole(Role.USER);
		return member;
	}
}
