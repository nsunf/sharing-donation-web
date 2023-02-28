package com.sharingdonation.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.DonationBoardComment;
import com.sharingdonation.entity.SharingBoardComment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DonationBoardCommentDto {

	private Long id;
	
	private String donationBoardId;
	
	private String commentMember;
	
	private String regTime;
	
	private String comment;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	
	//dto를 엔티티로 바꿈
	public DonationBoardComment writeComment() {
		return modelMapper.map(this, DonationBoardComment.class);
		
	}
	
	
	//엔티티를 dto로 바꿈
		public static DonationBoardCommentDto of(DonationBoardComment donationBoardComment) {
		    DonationBoardCommentDto donationBoardCommentDto = modelMapper.map(donationBoardComment, DonationBoardCommentDto.class);
		    donationBoardCommentDto.setRegTime(donationBoardComment.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a")));
			return donationBoardCommentDto;
		}
	
}
