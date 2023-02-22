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
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="area_id")
	private Area area;
	
	private String name;
	
	private String detail;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private int point;
	
	private LocalDateTime regTime;
	
	private LocalDateTime upDateTime;
	
	private String createBy;
	
	private String modifyBy;
	
	private String confirmYn;
	
	private String done;
	
	private String delYn;
	
}
