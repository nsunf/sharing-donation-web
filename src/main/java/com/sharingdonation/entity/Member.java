package com.sharingdonation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sharingdonation.entity.BaseEntity;
import com.sharingdonation.constant.Role;
import com.sharingdonation.dto.CorpFormDto;
import com.sharingdonation.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {
	
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String name;
	
	@Column(nullable = false, columnDefinition = "varchar(15)")
	private String cellphone;
	
	@Column(nullable = false, columnDefinition ="char(5)")
	private String zipCode;
	
	@Column(nullable = false, columnDefinition ="varchar(100)")
	private String address;
	
	@Column(nullable = false, columnDefinition ="varchar(100)")
	private String addressDetail;
	
	@Column(nullable = false, columnDefinition = "varchar(50)")
	private String nickName;
	
	private LocalDate birth;
	
	@Column(columnDefinition = "varchar(20)")
	private String comNum;
	
	@Column(columnDefinition = "varchar(15)")
	private String fax;
	
	@Column(nullable = false, columnDefinition = "int(11) default 0")
	private int point;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(5)")
	private Role role;
	
//	@Column(nullable = false)
//	private LocalDateTime regTime;
	
	@Column(nullable = false, columnDefinition = "char(1) default 'N'")
	private String delYn;
	
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setZipCode(memberFormDto.getZipCode());
		member.setAddress(memberFormDto.getAddress());
		member.setAddressDetail(memberFormDto.getAddressDetail());
		LocalDate date = LocalDate.parse(memberFormDto.getBirth(), DateTimeFormatter.ISO_DATE);
		member.setBirth(date);
		member.setCellphone(memberFormDto.getCellphone());
		member.setEmail(memberFormDto.getEmail());
		
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		
		member.setNickName(memberFormDto.getNickName());
		
		member.setDelYn("N");
		member.setRegTime(LocalDateTime.now());
		member.setRole(Role.USER);
		return member;
	}
	
	public static Member createCorp(CorpFormDto corpFormDto, PasswordEncoder passwordEncoder) {
		Member corp = new Member();
		corp.setNickName(corpFormDto.getNickName());
		corp.setName(corpFormDto.getName());
		corp.setComNum(corpFormDto.getComNum());
		corp.setZipCode(corpFormDto.getZipCode());
		corp.setAddress(corpFormDto.getAddress());
		corp.setAddressDetail(corpFormDto.getAddressDetail());
		corp.setCellphone(corpFormDto.getCellphone());
		corp.setFax(corpFormDto.getFax());
		corp.setRegTime(LocalDateTime.now());
		LocalDate date = LocalDate.parse(corpFormDto.getBirth(), DateTimeFormatter.ISO_DATE);
		corp.setBirth(date);
		corp.setEmail(corpFormDto.getEmail());
		
		String password = passwordEncoder.encode(corpFormDto.getPassword());
		corp.setPassword(password);
		
		corp.setDelYn("N");
		
		corp.setRole(Role.COM);
		return corp;
	}
	
	
}
