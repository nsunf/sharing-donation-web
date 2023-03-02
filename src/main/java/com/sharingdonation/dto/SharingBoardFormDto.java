package com.sharingdonation.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.SharingBoard;

import lombok.*;

@Getter
@Setter
public class SharingBoardFormDto {

	private String id; //식별아이디
	
	private Long sharing_id; //나눔물품 식별 아이디
	
	@NotBlank(message = "제목을 입력해주세요.")
	private String subject; //글제목
	
	@NotBlank(message = "상세내용을 입력해주세요.")
	private String content; //상세내용
	
	private LocalDateTime regTime; //글 등록 날짜
	
	private List<SharingBoardImgDto> sharingBoardImgDtoList = new ArrayList<>();
	
	private List<Long> sharingBoardImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//dto를 엔티티로 바꿈
	public SharingBoard createSharedPost () {
		this.regTime = LocalDateTime.now();
		return modelMapper.map(this, SharingBoard.class);
	}
	
	//엔티티를 dto로 바꿈
	public static SharingBoardFormDto of(SharingBoard sharingBoard) {
		SharingBoardFormDto sharingBoardFormDto = modelMapper.map(sharingBoard, SharingBoardFormDto.class);
		return sharingBoardFormDto;
	}

}
