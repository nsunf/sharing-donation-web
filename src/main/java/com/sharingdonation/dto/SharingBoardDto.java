package com.sharingdonation.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingBoard;

import lombok.*;

@Getter
@Setter
public class SharingBoardDto {
	
	private Long id; //식별 아이디
	
	private String reg_time; //글 등록 날짜
	
	//private String 
	
	private String subject; //글제목
	
	private String content; //상세내용
	
	private Integer sharing_board_comment_id; //댓글 식별 아이디
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//dto를 엔티티로 바꿈
	public SharingBoard sharedPost() {
		return modelMapper.map(this, SharingBoard.class);
	}
	//엔티티를 dto로 바꿈
	public static SharingBoardDto of(SharingBoard sharingBoard) {
		SharingBoardDto sharingBoardDto = modelMapper.map(sharingBoard, SharingBoardDto.class);
		sharingBoardDto.setReg_time(sharingBoard.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return sharingBoardDto;
	}
	
}