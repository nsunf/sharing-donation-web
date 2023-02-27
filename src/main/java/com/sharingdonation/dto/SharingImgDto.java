package com.sharingdonation.dto;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharingImgDto {
		private Long id;
		
		private String imgName;
		
		private String oriImgName;
		
		private String imgUrl;
		
		private String repImgYn;
		
		private static ModelMapper modelMapper = new ModelMapper();
		
		public static SharingImgDto of(SharingImg sharingImg) {
			return modelMapper.map(sharingImg, SharingImgDto.class);
		}
}
