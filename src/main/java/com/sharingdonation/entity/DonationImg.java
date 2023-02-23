package com.sharingdonation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="donation_img")
@Getter
@Setter
@ToString
public class DonationImg {
	@Id
	@Column(name="donation_img_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length=100)
	private String imgName;
	
	@Column(nullable = false, length=100)
	private String oriImgName;
	
	@Column(nullable = false, length=100)
	private String imgUrl;
	
	@Column(nullable = false, columnDefinition ="char(1) default 'N'")
	private String repimgYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "donation_id")
	private Donation donation;
	
	public void updateDonationImg(String imgName, String oriImgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
}
