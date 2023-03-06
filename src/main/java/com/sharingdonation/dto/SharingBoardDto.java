package com.sharingdonation.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.Sharing;
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
	
	private String imgUrl; //이미지 url
	
	private Long boardHeartCount; //좋아요 갯수
	
	private String content; //상세내용
	
	private Long commentCount; //댓글 갯수

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