package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingBoardImg;

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

	public static SharingBoardImgDto of(SharingBoardImg sharingBoardImg) {
		return modelMapper.map(sharingBoardImg, SharingBoardImgDto.class);
	}

}
