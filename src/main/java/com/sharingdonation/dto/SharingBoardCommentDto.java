package com.sharingdonation.dto;

import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.SharingBoardComment;

import lombok.*;

@Getter
@Setter
@ToString
public class SharingBoardCommentDto {
	private Long id; //식별아이디
	
	private String sharing_board_id; //게시글 식별아이디
	
	private String comment_member; //댓글 작성한 사람
	
	private String comment_email;
	
	private String regTime; //댓글 등록 날짜
	
	private String comment; //댓글 내용
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//dto를 엔티티로 바꿈
	public SharingBoardComment writeComment() {
		return modelMapper.map(this, SharingBoardComment.class);
	}
	
	//엔티티를 dto로 바꿈
	public static SharingBoardCommentDto of(SharingBoardComment sharingBoardComment) {
		SharingBoardCommentDto sharingBoardCommentDto = modelMapper.map(sharingBoardComment, SharingBoardCommentDto.class);
		sharingBoardCommentDto.setRegTime(sharingBoardComment.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a")));
		return sharingBoardCommentDto;
	}
	
}
