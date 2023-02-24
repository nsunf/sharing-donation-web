package com.sharingdonation.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingBoard;

import lombok.*;

@Getter
@Setter
public class SharingBoardDto {
	
	private Long id; //식별 아이디
	
	private LocalDateTime reg_time; //글 등록 날짜
	
	private String nick_name; //글 작성자 (관리자)
	
	private String subject; //글제목
	
	private String content; //상세내용
	
	private Integer sharing_board_comment_id; //댓글 식별 아이디
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public SharingBoard sharedPost() {
		return modelMapper.map(this, SharingBoard.class);
	}
	
	public static SharingBoardDto of(SharingBoard sharingBoard) {
		return modelMapper.map(sharingBoard, SharingBoardDto.class);
	}
	
}