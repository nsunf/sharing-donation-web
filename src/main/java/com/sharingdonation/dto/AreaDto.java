package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.Area;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaDto {
	private Long id;
	
	private String sido;
	
	private String gugun;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static AreaDto of(Area area) {
		return modelMapper.map(area, AreaDto.class);
	}
}
