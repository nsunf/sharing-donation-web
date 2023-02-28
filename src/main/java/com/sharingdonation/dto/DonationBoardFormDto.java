package com.sharingdonation.dto;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.BaseTimeEntity;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardFormDto{
	private String id;
	
	private Long donationId;
	
	@NotBlank(message = "Must enter the post subject.")
	private String subject;
	
	@NotBlank(message = "Must enter the post content.")
	private String content;
	
	private LocalDateTime regTime;
	
	private Long heartCount;
	private DonationBoardSelectDto donationBoardSelectDto;
	
	
	private List<DonationBoardImgDto> donationBoardImgDtoList = new ArrayList<>();
	private List<Long> donationBoardImgIds = new ArrayList<>();
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	 
	public DonationBoard createDonationBoard() {
		this.regTime =  LocalDateTime.now();
		return modelMapper.map(this, DonationBoard.class);
	}
	
	public static DonationBoardFormDto of(DonationBoard donationBoard) {
		DonationBoardFormDto result = modelMapper.map(donationBoard, DonationBoardFormDto.class);
		//result.setRegTime(donationBoard.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		Donation donation = donationBoard.getDonation();
		result.setDonationBoardSelectDto(DonationBoardSelectDto.of(donation));
		return result;
	}
	

}
