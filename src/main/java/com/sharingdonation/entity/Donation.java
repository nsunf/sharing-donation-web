package com.sharingdonation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

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
	
	private String memberId;
	
	@Column(nullable = false, length=200)
	private String donationName;
	
	@Column(nullable = false, length=100)
	private String donationPerson;
	
	@Column(nullable = false, length=15)
	private String donationTel;
	
	private String subject;
	
	@Lob
	@Column(nullable = false)
	private String detail;
	
	private int price;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String address;
	
	private int goalPoint;
	
	private LocalDateTime regTime;
	
	@Column(nullable = false, columnDefinition ="char", length=1)
	@ColumnDefault("N")
	private String confirmYn;
	
	@Column(nullable = false, columnDefinition ="char", length=1)
	@ColumnDefault("N")
	private String done;
	
	@Column(nullable = false, columnDefinition ="char", length=1)
	@ColumnDefault("N")
	private String delYn;
	
	
}
