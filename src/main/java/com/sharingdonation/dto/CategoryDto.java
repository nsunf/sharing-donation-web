package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	private Long id;
	
	private String categoryName;

	private static ModelMapper modelMapper = new ModelMapper();
	
	public static CategoryDto of (Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}
}
