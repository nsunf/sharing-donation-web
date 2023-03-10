package com.sharingdonation.entity;

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

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="story")
@Getter
@Setter
@ToString
public class Story {
	@Id
	@Column(name="story_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sharing_id")
	private Sharing sharing;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@Column(nullable = false, columnDefinition = "varchar(1000)")
	private String content;
	
	@Column(nullable = false)
	private LocalDateTime regTime;
	
	@Column(nullable = false, columnDefinition = "char(1) default 'N'")
	private String chooseYn;
	
	@Column(nullable = false, columnDefinition = "char(1) default 'N'")
	private String delYn;
}
