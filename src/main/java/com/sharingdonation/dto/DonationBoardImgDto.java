package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.DonationBoardImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardImgDto {
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static DonationBoardImgDto of(DonationBoardImg donationBoardImg) {
		return modelMapper.map(donationBoardImg, DonationBoardImgDto.class);
		
	}
	
}
