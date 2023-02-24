package com.sharingdonation.dto;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.DonationBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardFormDto {
	private String id;
	
	@NotBlank(message = "Must enter the post subject.")
	private String subject;
	
	@NotBlank(message = "Must enter the post content.")
	private String content;
	
	private LocalDateTime regTime;
	
	
	private List<DonationBoardImgDto> donationBoardImgDtoList = new ArrayList<>();
	private List<Long> donationBoardImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	 
	public DonationBoard createDonationBoard() {
		return modelMapper.map(this, DonationBoard.class);
	}
	
	public static DonationBoardFormDto of(DonationBoard donationBorad) {
		return modelMapper.map(donationBorad, DonationBoardFormDto.class);
		
	}
	

}
