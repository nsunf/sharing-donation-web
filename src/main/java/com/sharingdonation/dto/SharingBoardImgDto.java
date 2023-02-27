package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingImg;

import lombok.*;

@Getter
@Setter
public class SharingBoardImgDto {

private Long id;
	
	private String imgName;

	private String oriImgName;

	private String imgUrl;

	private String repimgYn;

	private static ModelMapper modelMapper = new ModelMapper();

	public static SharingImgDto of(SharingImg sharingImg) {
		return modelMapper.map(sharingImg, SharingImgDto.class);
	}
		
}
