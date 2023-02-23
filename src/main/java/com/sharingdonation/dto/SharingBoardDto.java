package com.sharingdonation.dto;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingBoard;

import lombok.*;

@Getter
@Setter
public class SharingBoardDto {
	
	private String story_id; //나눔 받은 자의 아이디
	
	private String member_id; //나눔 하는 자의 아이디
	
	private String director; //담당자, 글 등록한 관리자. -> member의 admin인디.. 이게 맞는지...?
	
	private LocalDate completeDate; //나눔완료 날짜
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public SharingBoard completeShare() {
		return modelMapper.map(this, SharingBoard.class);
	}
	 
	
	
	
}