package com.sharingdonation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharingdonation.dto.SharingBoardDto;
import com.sharingdonation.entity.SharingBoard;
import com.sharingdonation.repository.SharingBoardImgRepository;
import com.sharingdonation.repository.SharingBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SharingBoardService {
	private final SharingBoardRepository sharingBoardRepository;
	private final SharingBoardImgRepository sharingBoardImgRepository;
	
	//나눔완료 게시글 리스트 가져오기
	@Transactional(readOnly = true)
	public List<SharingBoardDto> getCompletePost(){
		List<SharingBoard> sharingBoardList = sharingBoardRepository.findAll();
		List<SharingBoardDto> sharingBoardDtoList = new ArrayList<>();
		
		//앤티티 객체를 dto객체로 변환
		for(SharingBoard sharingBoard : sharingBoardList) {
			SharingBoardDto sharingBoardDto = new SharingBoardDto();
			sharingBoardDtoList.add(sharingBoardDto);
		}
		return sharingBoardDtoList;
	}
	
	
}
