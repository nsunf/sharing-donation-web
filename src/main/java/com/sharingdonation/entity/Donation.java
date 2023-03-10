package com.sharingdonation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import com.sharingdonation.dto.DonationAdminFormDto;
import com.sharingdonation.dto.DonationFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@DynamicInsert
@Entity
@Table(name="donation")
@Getter
@Setter
@ToString
public class Donation extends BaseTimeEntity{
	@Id
	@Column(name="donation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
//	@CreationTimestamp
//	@Column(nullable = false, updatable = false)
//	private LocalDateTime regTime;
	
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
//
//		this.startDate = (LocalDate) donationFormDto.getStartDate();
//		this.endDate = (LocalDate) donationFormDto.getEndDate();
		this.address = donationFormDto.getAddress();
		this.goalPoint = donationFormDto.getGoalPoint();
		
	}
	
	//모든엔티티가노출되면 보안상안좋아서 관리자와 일반을 나눔
	public void updateAdminDonation(DonationAdminFormDto donationAdminFormDto) {
		
//		System.out.println("donation entity updateAdminDonation");
//		System.out.println(donationAdminFormDto.getStartDate() +":"+ donationAdminFormDto.getEndDate());
//		System.out.println();
		
		this.donationName = donationAdminFormDto.getDonationName();
		this.donationPerson = donationAdminFormDto.getDonationPerson();
		this.donationTel = donationAdminFormDto.getDonationTel();
		this.subject = donationAdminFormDto.getSubject();
		this.detail = donationAdminFormDto.getDetail();
		this.price = donationAdminFormDto.getPrice();
//		System.out.println("before");
		LocalDate startDate = (donationAdminFormDto.getStartDate() != null && !donationAdminFormDto.getStartDate().isEmpty()) ? LocalDate.parse(donationAdminFormDto.getStartDate(), DateTimeFormatter.ISO_DATE) : null ;// LocalDate.parse("0000-00-00", DateTimeFormatter.ISO_DATE);
//		String startDate = (donationAdminFormDto.getStartDate() != null && !donationAdminFormDto.getStartDate().isEmpty()) ? "not null" : null ;
//		System.out.println(" startDate : " +startDate);
		this.startDate = startDate;
		LocalDate endDate = (donationAdminFormDto.getEndDate() != null && !donationAdminFormDto.getEndDate().isEmpty()) ? LocalDate.parse(donationAdminFormDto.getEndDate(), DateTimeFormatter.ISO_DATE) : null;//LocalDate.parse("0000-00-00", DateTimeFormatter.ISO_DATE);
//		String endDate = (donationAdminFormDto.getEndDate() != null && !donationAdminFormDto.getEndDate().isEmpty()) ? "not null" : null;//LocalDate.parse("0000-00-00", DateTimeFormatter.ISO_DATE);
//		System.out.println(" endDate : " +endDate);
		this.endDate = endDate;
//		this.startDate = (LocalDate) donationAdminFormDto.getStartDate();
//		this.endDate = (LocalDate) donationAdminFormDto.getEndDate();
		this.address = donationAdminFormDto.getAddress();
		this.goalPoint = donationAdminFormDto.getGoalPoint();
		this.delYn = donationAdminFormDto.getDelYn();
		this.confirmYn = donationAdminFormDto.getConfirmYn();
		this.done = donationAdminFormDto.getDone();
	}
}
