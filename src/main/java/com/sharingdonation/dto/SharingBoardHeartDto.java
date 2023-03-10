package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingBoardHeart;

import lombok.*;

@Getter
@Setter
public class SharingBoardHeartDto {
	private Long id;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static SharingBoardHeartDto of (SharingBoardHeart sharingBoardHeart) {
		return modelMapper.map(sharingBoardHeart, SharingBoardHeartDto.class);
	}
}
