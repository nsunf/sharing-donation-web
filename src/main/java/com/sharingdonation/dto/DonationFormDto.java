package com.sharingdonation.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.Donation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationFormDto {
	private Long id;
	
	@NotBlank(message = "기부처명은 필수 입력 값입니다.")
	private String donationName;
	
	@NotBlank(message = "기부처담당자명은 필수 입력 값입니다.")
	private String donationPerson;
	
	@NotBlank(message = "기부전화번호는 필수 입력 값입니다.")
	private String donationTel;
	
	@NotBlank(message = "제목은 필수 입력 값입니다.")
	private String subject;
	
	@NotBlank(message = "기부 상세설명은 필수 입력 값입니다.")
	private String detail;
	
	private String zipCode;
	
	private String address;
	
	private String addressDetail;
	
	private int price;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@NotNull(message = "목표포인트는 필수 입력 값입니다.")
	private int goalPoint;
	
	private List<DonationImgDto> donationImgDtoList = new ArrayList<>();
	
	private List<Long> donateionImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Donation createDonation() {
		return modelMapper.map(this,  Donation.class);
	}
	
	public static DonationFormDto of(Donation donation) {
		return modelMapper.map(donation, DonationFormDto.class);
	}
}
