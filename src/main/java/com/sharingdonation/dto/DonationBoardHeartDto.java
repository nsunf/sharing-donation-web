package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.DonationBoardHeart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardHeartDto {
	private Long id;
	
	private String donationBoardId;
	
	private String heartMember;
	
	private String regTime;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static DonationBoardHeartDto of(DonationBoardHeart donationBoardHeart) {
		return modelMapper.map(donationBoardHeart, DonationBoardHeartDto.class);
		
	}
}
