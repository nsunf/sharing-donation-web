package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingHeart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharingHeartDto {
	private Long id;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static SharingHeartDto of(SharingHeart sharingHeart) {
		return modelMapper.map(sharingHeart, SharingHeartDto.class);
	}
}
