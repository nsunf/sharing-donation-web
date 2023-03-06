package com.sharingdonation.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.Donation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationAdminFormDto extends DonationFormDto {
	private String delYn;
	
	private String confirmYn;
	
	private String done;

	
	private String status;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static DonationAdminFormDto of(Donation donation) {
		return modelMapper.map(donation, DonationAdminFormDto.class);
	}
}
