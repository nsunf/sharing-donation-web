package com.sharingdonation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationDto {
	private Long id;
	
	private String donationName;
	
	private int memberId;
	
	private String donationPerson;
	
	private String donationTel;
	
	private String subject;
	
	private String detail;
	
	private int price;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String address;
	
	private int goalPoint;
	
	private LocalDateTime regTime;
	
	private String confirmYn;
	
	private String done;
	
	private String delYn;
	
}
