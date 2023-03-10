package com.sharingdonation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationBoardDto {
	
	private Long id;
	
	private Long donationId;
	
	private String subject;
	
	private String content;
	
	private String regTime;
	
	private String imgUrl;
	
	private Long commentCount; //댓글 갯수
	
	private Long donationBoardheartCount;
	
	private String donationPerson;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private DonationBoardSelectDto donationBoardSelectDto;
	
	//private static ModelMapper modelMapper = new ModelMapper();
	
//	//dto를 엔티티로 바꿈
//	public DonationBoard donationPost() {
//		return modelMapper.map(this, DonationBoard.class);
//	}
//	
//	//엔티티를 dto로 바꿈
//	public static DonationBoardDto of(DonationBoard donationBoard) {
//		return modelMapper.map(donationBoard, DonationBoardDto.class);
//	}
	
	@QueryProjection
	public DonationBoardDto(Long id, Long donationId, String subject, String content,  String regTime, String imgUrl
			, Long commentCount, Long donationBoardheartCount, String donationPerson, LocalDate startDate, LocalDate endDate) {
		this.id = id;
		this.donationId = donationId;
		this.subject = subject;
		this.content = content;
		this.regTime = regTime;//.formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.imgUrl = imgUrl;
		this.commentCount = commentCount;
		this.donationBoardheartCount = donationBoardheartCount;
		this.donationPerson = donationPerson;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
//	public static DonationBoardDto of(DonationBoard donationBoard) {
//		DonationBoardDto result = modelMapper.map(donationBoard, DonationBoardDto.class); 
//		Donation donation = donationBoard.getDonation();
//		result.setDonationBoardSelectDto(DonationBoardSelectDto.of(donation));
//		return result;
//		
//	}

	
}
