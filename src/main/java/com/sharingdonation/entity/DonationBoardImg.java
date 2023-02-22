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
@Table(name="donateion_board_img")
@Getter
@Setter
@ToString
public class DonationBoardImg {
	@Id
	@Column(name="donation_board_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="donation_board_img_id")
	private DonationBoardImg donationBoardImg;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
}
