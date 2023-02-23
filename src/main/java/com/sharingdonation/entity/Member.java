package com.sharingdonation.entity;

import java.time.LocalDate;
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
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String name;
	
	@Column(nullable = false, columnDefinition = "varchar(15)")
	private String cellphone;
	
	@Column(nullable = false, columnDefinition = "varchar(200)")
	private String address;
	
	@Column(nullable = false, columnDefinition = "varchar(50)")
	private String nickName;
	
	private LocalDate birth;
	
	@Column(nullable = false, columnDefinition = "varchar(20)")
	private String comNum;
	
	@Column(nullable = false, columnDefinition = "varchar(15)")
	private String fax;
	
	@Column(nullable = false, columnDefinition = "int(1) default 0")
	private int point;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(5)")
	private Role role;
	
	private LocalDateTime regTime;
	
	@Column(nullable = false, columnDefinition = "char(1) default 'N'")
	private String delYn;
	
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setAddress(memberFormDto.getAddress());
		member.setBirth(memberFormDto.getBirth());
		member.setCellphone(memberFormDto.getCellphone());
		member.setEmail(memberFormDto.getEmail());
		
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		
		member.setNickName(memberFormDto.getNickName());
		
		member.setRole(Role.USER);
		return member;
	}
}
