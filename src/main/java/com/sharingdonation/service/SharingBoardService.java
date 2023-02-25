package com.sharingdonation.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharingdonation.dto.SharingBoardDto;
import com.sharingdonation.entity.SharingBoard;
import com.sharingdonation.entity.Story;
import com.sharingdonation.repository.SharingBoardImgRepository;
import com.sharingdonation.repository.SharingBoardRepository;
import com.sharingdonation.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SharingBoardService {
	private final SharingBoardRepository sharingBoardRepository;
	private final SharingBoardImgRepository sharingBoardImgRepository;
	private final StoryRepository storyRepository;

	// 나눔완료 게시글 리스트 가져오기
	@Transactional(readOnly = true)
	public List<SharingBoardDto> getCompletePostList() {
		List<SharingBoard> sharingBoardList = sharingBoardRepository.findAll();
		List<SharingBoardDto> sharingBoardDtoList = new ArrayList<>();

		for (SharingBoard sharingBoard : sharingBoardList) {
			SharingBoardDto sharingBoardDto = SharingBoardDto.of(sharingBoard);

			sharingBoardDtoList.add(sharingBoardDto);
		}

		return sharingBoardDtoList;
	}

	// 나눔완료 게시글 가져오기
	@Transactional
	public SharingBoardDto getCompletePost(Long id) {

		SharingBoard sharingBoard = sharingBoardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		SharingBoardDto sharingBoardDto = SharingBoardDto.of(sharingBoard);
		
		String sharedPostNickname = sharingBoard.getSharing().getMember().getNickName();
		sharingBoardDto.setSharing_member(sharedPostNickname);
		
		
		Story story = storyRepository.findByIdAndChooseYn(id,"Y");
		String chooseYStory	= story.getMember().getNickName();
		sharingBoardDto.setStory_member(chooseYStory);
		
		
		return sharingBoardDto;
	}
	
	/*
	// 나눔완료 게시글 댓글
	public SharingBoard
	*/
}
