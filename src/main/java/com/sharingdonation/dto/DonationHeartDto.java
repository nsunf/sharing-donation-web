package com.sharingdonation.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.DonationHeart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationHeartDto {
	private Long id;
	
	private LocalDateTime regTime;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static DonationHeartDto of(DonationHeart donationHeart) {
		return modelMapper.map(donationHeart, DonationHeartDto.class);
	}
}
