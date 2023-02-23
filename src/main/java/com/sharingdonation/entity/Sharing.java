package com.sharingdonation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="sharing")
@Getter
@Setter
@ToString
public class Sharing {
	@Id
	@Column(name="sharing_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //식별 아이디
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category; //품목 카테고리
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member; //회원
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="area_id")
	private Area area; //지역
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String name;
	
	@Column(nullable = false, columnDefinition = "text")
	private String detail;
	
	private LocalDate startDate; //게시 시작일
	
	private LocalDate endDate; //게시 마감일
	
	@Column(nullable = false, columnDefinition = "int(11) default 0")
	private int point;
	
	@Column(nullable = false)
	private LocalDateTime regTime;
	
	private LocalDateTime upDateTime; //게시글 수정날짜
	
	@Column(nullable = false)
	private String createBy;
	
	private String modifyBy; //수정자
	
	@Column(nullable = false, columnDefinition = "char(1) default 'N'")
	private String confirmYn;
	
	@Column(nullable = false, columnDefinition = "char(1) default 'N'")
	private String done;
	
	@Column(nullable = false, columnDefinition = "char(1) default 'N'")
	private String delYn;
	
}
