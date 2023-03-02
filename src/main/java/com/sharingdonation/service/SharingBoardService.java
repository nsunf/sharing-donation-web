package com.sharingdonation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharingdonation.dto.SharingBoardCommentDto;
import com.sharingdonation.dto.SharingBoardDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.SharingBoard;
import com.sharingdonation.entity.SharingBoardComment;
import com.sharingdonation.entity.Story;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.SharingBoardCommentRepository;
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
	private final SharingBoardCommentRepository sharingBoardCommentRepository;
	private final MemberRepository memberRepository;

	// 나눔완료 게시글 리스트 가져오기
	@Transactional(readOnly = true)
	public List<SharingBoardDto> getCompletePostList() {
		List<SharingBoard> sharingBoardList = sharingBoardRepository.findAll();
		List<SharingBoardDto> sharingBoardDtoList = new ArrayList<>();

		for (SharingBoard sharingBoard : sharingBoardList) {
			SharingBoardDto sharingBoardDto = SharingBoardDto.of(sharingBoard);
			
			Long commentCount =sharingBoardCommentRepository.countBySharingBoardId(sharingBoard.getId());
			sharingBoardDto.setCommentCount(commentCount);
			
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

		Story story = storyRepository.findByIdAndChooseYn(id, "Y");
		String chooseYStory = story.getMember().getNickName();
		sharingBoardDto.setStory_member(chooseYStory);

		return sharingBoardDto;
	}
	
	// 나눔완료 게시글 여러 댓글들 보여줌
	@Transactional
	public List<SharingBoardCommentDto> getBoardCommentList(Long id) {
		System.out.println("확인 : start");
		List<SharingBoardComment> sharingBoardCommentList = sharingBoardCommentRepository.findBySharingBoardId(id);
		System.out.println("확인 : start1" + sharingBoardCommentList);
		List<SharingBoardCommentDto> sharingBoardCommentDtoList = new ArrayList<>();
		
		for (SharingBoardComment sharingBoardComment : sharingBoardCommentList) {
			SharingBoardCommentDto sharingBoardCommentDto = SharingBoardCommentDto.of(sharingBoardComment);
			
			String sharedWriteCommentMember = sharingBoardComment.getMember().getNickName();
			sharingBoardCommentDto.setComment_member(sharedWriteCommentMember);
			System.out.println("확인 : "+sharingBoardCommentDto.getComment());
			
			
			sharingBoardCommentDtoList.add(sharingBoardCommentDto);

		}
		return sharingBoardCommentDtoList;
	}
	
	//댓글 작성
	public Long insertComment(Long member_id, Long sharedBoard_id, String comment){
		Member sharedCommentMember = memberRepository.findById(member_id).orElseThrow(EntityNotFoundException::new);;
		SharingBoard sharedCommentPost = sharingBoardRepository.findById(sharedBoard_id).orElseThrow(EntityNotFoundException::new);;
		
		SharingBoardComment sharingBoardComment = new SharingBoardComment();
		sharingBoardComment.setMember(sharedCommentMember);
		sharingBoardComment.setSharingBoard(sharedCommentPost);
		sharingBoardComment.setComment(comment);
		sharingBoardComment.setRegTime(LocalDateTime.now());
		
		sharingBoardCommentRepository.save(sharingBoardComment);
		
		return sharingBoardComment.getId();
	}
}
