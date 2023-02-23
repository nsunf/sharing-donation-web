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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.sharingdonation.dto.DonationFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="donation")
@Getter
@Setter
@ToString
public class Donation {
	@Id
	@Column(name="donation_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@Column(nullable = false, length=200)
	private String donationName;
	
	@Column(nullable = false, length=100)
	private String donationPerson;
	
	@Column(nullable = false, length=15)
	private String donationTel;
	
	@Column(nullable = false, length=100)
	private String subject;
	
//	@Lob
	@Column(nullable = false, columnDefinition = "text")
	private String detail;
	
	@Column(nullable = false, columnDefinition = "int(11) default 0")
	private int price;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@Column(nullable = false, columnDefinition ="char(5)")
	private String zipCode;
	
	@Column(nullable = false, columnDefinition ="varchar(100)")
	private String address;
	
	@Column(nullable = false, columnDefinition ="varchar(100)")
	private String addressDetail;
	
	@Column(nullable = false, columnDefinition = "int(11) default 0")
	private int goalPoint;
	
	@Column(nullable = false)
	private LocalDateTime regTime;
	
	@Column(nullable = false, columnDefinition ="char(1) default 'N'")
//	@NotNull
//	@ColumnDefault("N")
	private String confirmYn;
	
	@Column(nullable = false, columnDefinition ="char(1) default 'N'")
//	@NotNull
//	@ColumnDefault("N")
	private String done;
	
	@Column(nullable = false, columnDefinition ="char(1) default 'N'")
	private String delYn;
	
	public void updateDonation(DonationFormDto donationFormDto) {
		this.donationName = donationFormDto.getDonationName();
		this.donationPerson = donationFormDto.getDonationPerson();
		this.donationTel = donationFormDto.getDonationTel();
		this.subject = donationFormDto.getSubject();
		this.detail = donationFormDto.getDetail();
		this.price = donationFormDto.getPrice();
		this.startDate = donationFormDto.getStartDate();
		this.endDate = donationFormDto.getEndDate();
		this.address = donationFormDto.getAddress();
		this.goalPoint = donationFormDto.getGoalPoint();
	}
}
