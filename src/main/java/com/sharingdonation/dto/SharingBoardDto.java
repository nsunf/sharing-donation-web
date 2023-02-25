package com.sharingdonation.dto;

import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingBoard;

import lombok.*;

@Getter
@Setter
public class SharingBoardDto {
	
	private Long id; //식별 아이디
	
	private String regTime; //글 등록 날짜
	
	private String sharing_member; //나눔상품 등록한 사람
	
	private String story_member; //사연채택 된 사람
	
	private String subject; //글제목
	
	private String content; //상세내용

	private static ModelMapper modelMapper = new ModelMapper();
	
	//dto를 엔티티로 바꿈
	public SharingBoard sharedPost() {
		return modelMapper.map(this, SharingBoard.class);
	}
	//엔티티를 dto로 바꿈
	public static SharingBoardDto of(SharingBoard sharingBoard) {
		SharingBoardDto sharingBoardDto = modelMapper.map(sharingBoard, SharingBoardDto.class);
		sharingBoardDto.setRegTime(sharingBoard.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return sharingBoardDto;
	}
	
}