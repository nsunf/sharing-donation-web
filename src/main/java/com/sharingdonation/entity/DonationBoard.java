package com.sharingdonation.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sharingdonation.dto.DonationBoardFormDto;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="donation_board")
@Getter
@Setter
@ToString
public class DonationBoard {
	@Id
	@Column(name="donation_board_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="donation_id")
	private Donation donation;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String subject;
	 
	@Column(nullable = false, columnDefinition = "text")
	private String content;
	
	@Column(nullable = false)
	private LocalDateTime regTime;

	public void updateDonationBoard(DonationBoardFormDto donationBoardFormDto) {
		this.subject = donationBoardFormDto.getSubject();
		this.content = donationBoardFormDto.getContent();
		this.regTime = donationBoardFormDto.getRegTime();
		
		
		
	}
}
