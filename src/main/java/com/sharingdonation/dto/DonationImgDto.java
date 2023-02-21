package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.DonationImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationImgDto {
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static DonationImgDto of(DonationImg donationImg) {
		return modelMapper.map(donationImg, DonationImgDto.class);
	}
}
