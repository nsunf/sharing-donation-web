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
	
	private String name; //나눔물품명
	
	private String detail; //물품 상세설명
	
	private LocalDate startDate; //게시 시작일
	
	private LocalDate endDate; //게시 마감일
	
	private int point; //기부포인트
	
	private LocalDateTime regTime; //게시글 등록날짜
	
	private LocalDateTime upDateTime; //게시글 수정날짜
	
	private String createBy; //등록자
	
	private String modifyBy; //수정자
	
	private String confirmYn; //승인여부
	
	private String done; //완료여부
	
	private String delYn; //삭제여부
	
}
