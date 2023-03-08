package com.sharingdonation.dto;

import java.time.format.DateTimeFormatter;

import com.sharingdonation.constant.HeartType;

import lombok.Getter;
import lombok.Setter;

import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationBoard;
import com.sharingdonation.entity.DonationBoardHeart;
import com.sharingdonation.entity.DonationHeart;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.SharingBoard;
import com.sharingdonation.entity.SharingBoardHeart;
import com.sharingdonation.entity.SharingHeart;

@Getter
@Setter
public class HeartHistDto {
	private Long id;
	
	private Long targetId;
	
	private HeartType type;
	
	private String memberEmail;
	
	private String memberNickname;
	
	private String title;
	
	private String comment;

	private String regTime;
	
	public static HeartHistDto valueOf(SharingHeart heart) {
		Member member = heart.getMember();
		Sharing sharing = heart.getSharing();

		HeartHistDto dto = new HeartHistDto();
		dto.setId(heart.getId());
		dto.setTargetId(sharing.getId());
		dto.setMemberEmail(member.getEmail());
		dto.setMemberNickname(member.getNickName());
		dto.setTitle(sharing.getName());
		dto.setComment(sharing.getArea().getGugun());
		dto.setRegTime(heart.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return dto;
	}

	public static HeartHistDto valueOf(SharingBoardHeart heart) {
		Member member = heart.getMember();
		SharingBoard board = heart.getSharingBoard();
		Sharing sharing = board.getSharing();

		HeartHistDto dto = new HeartHistDto();
		dto.setId(heart.getId());
		dto.setTargetId(board.getId());
		dto.setMemberEmail(member.getEmail());
		dto.setMemberNickname(member.getNickName());
		dto.setTitle(board.getSubject());
		dto.setComment(sharing.getArea().getGugun());
		dto.setRegTime(heart.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return dto;
	}

	public static HeartHistDto valueOf(DonationHeart heart) {
		Member member = heart.getMember();
		Donation donation = heart.getDonation();
		
		HeartHistDto dto = new HeartHistDto();
		dto.setId(heart.getId());
		dto.setTargetId(donation.getId());
		dto.setMemberEmail(member.getEmail());
		dto.setMemberNickname(member.getNickName());
		dto.setTitle(donation.getSubject());
		dto.setComment(donation.getDonationName());
		dto.setRegTime(heart.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return dto;
	}

	public static HeartHistDto valueOf(DonationBoardHeart heart) {
		Member member = heart.getMember();
		DonationBoard board = heart.getDonationBoard();
		Donation donation = board.getDonation();
		
		HeartHistDto dto = new HeartHistDto();
		dto.setTargetId(board.getId());
		dto.setId(heart.getId());
		dto.setMemberEmail(member.getEmail());
		dto.setMemberNickname(member.getNickName());
		dto.setTitle(board.getSubject());
		dto.setComment(donation.getDonationName());
		dto.setRegTime(heart.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return dto;
	}
	
}
