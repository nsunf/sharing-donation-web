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

import com.sharingdonation.constant.MoveType;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="point")
@Getter
@Setter
@ToString
public class Point {
	@Id
	@Column(name="point_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sharing_id")
	@Column(nullable = true)
	private Sharing sharing;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="donation_id")
	@Column(nullable = true)
	private Donation donation;
	
	private MoveType moveType;
	
	private int point;
	
	private String comment;
	
	private LocalDateTime regTime;
}
