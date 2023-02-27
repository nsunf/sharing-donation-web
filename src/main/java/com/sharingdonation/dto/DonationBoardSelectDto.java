package com.sharingdonation.dto;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.Donation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardSelectDto {
	private Long id;
	
	private String donationName;
	
	private String subject;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String nickName;
	
	private String donationPerson;
	
	private String address;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static DonationBoardSelectDto of(Donation donation) {
		return modelMapper.map(donation, DonationBoardSelectDto.class);
	}
}
