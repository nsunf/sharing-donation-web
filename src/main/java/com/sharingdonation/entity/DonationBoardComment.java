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
@Table(name="donation_board_comment")
@Getter
@Setter
@ToString
public class DonationBoardComment {
	@Id
	@Column(name="donation_board_comment")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="doantion_board_id")
	private DonationBoard donationBoard;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@Column(nullable = false)
	private String comment;
	
	@Column(nullable = false)
	private LocalDateTime regTime;
}
