package com.sharingdonation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.sharingdonation.dto.SharingBoardCommentDto;
import com.sharingdonation.dto.SharingBoardDto;
import com.sharingdonation.dto.SharingBoardFormDto;
import com.sharingdonation.dto.SharingBoardImgDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.SharingBoard;
import com.sharingdonation.entity.SharingBoardComment;
import com.sharingdonation.entity.SharingBoardHeart;
import com.sharingdonation.entity.SharingBoardImg;
import com.sharingdonation.entity.Story;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.SharingBoardCommentRepository;
import com.sharingdonation.repository.SharingBoardHeartRepository;
import com.sharingdonation.repository.SharingBoardImgRepository;
import com.sharingdonation.repository.SharingBoardRepository;
import com.sharingdonation.repository.SharingRepository;
import com.sharingdonation.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SharingBoardService {
	private final SharingBoardRepository sharingBoardRepository;
	private final StoryRepository storyRepository;
	private final SharingBoardCommentRepository sharingBoardCommentRepository;
	private final MemberRepository memberRepository;
	private final SharingRepository sharingRepository;
	private final SharingBoardImgService sharingBoardImgService;
	private final SharingBoardHeartRepository sharingBoardHeartRepository;
	private final SharingBoardImgRepository sharingBoardImgRepository;
	
	//게시판 보여줌. 페이징, 검색
	@Transactional(readOnly = true)
	public Page<SharingBoardDto> getCompletePostPage(String searchName, Pageable pageable){
		Page<SharingBoard> sharingPage = sharingBoardRepository.findBySharingNameContainsOrSubjectContainsOrderByRegTimeDesc(searchName, searchName, pageable);
		Page<SharingBoardDto> sharingBoardDtoPage = sharingPage.map(sharingBoard -> {
			SharingBoardDto sharingBoardDto = SharingBoardDto.of(sharingBoard);
			
			String imgUrl = sharingBoardImgService.getSharingBoardImg(sharingBoard.getId()).getImgUrl();
			sharingBoardDto.setImgUrl(imgUrl);
			
			Long boardHeartCount = sharingBoardHeartRepository.countBySharingBoardId(sharingBoardDto.getId());
			sharingBoardDto.setBoardHeartCount(boardHeartCount);
			
			Long commentCount = sharingBoardCommentRepository.countBySharingBoardId(sharingBoard.getId());
			sharingBoardDto.setCommentCount(commentCount);
			
			
			
			return sharingBoardDto;
		});
		
		return sharingBoardDtoPage;
	}

	//나눔완료 게시글 가져옴
		public SharingBoardFormDto getSharingBoardFormDetail(Long sharingBoard_id) {
			List<SharingBoardImg> sharingBoardImgList = sharingBoardImgRepository.findBySharingBoardId(sharingBoard_id);
			List<SharingBoardImgDto> sharingBoardImgDtoList = new ArrayList<>();
			
			for(SharingBoardImg sharingBoardImg : sharingBoardImgList) {
				SharingBoardImgDto sharingBoardImgDto = SharingBoardImgDto.of(sharingBoardImg);
				
				sharingBoardImgDtoList.add(sharingBoardImgDto);
			}
			
			SharingBoard sharingBoard = sharingBoardRepository.findById(sharingBoard_id)
										.orElseThrow(EntityNotFoundException::new);
			
			SharingBoardFormDto sharingBoardFormDto = SharingBoardFormDto.of(sharingBoard);
			
			sharingBoardFormDto.setSharingBoardImgDtoList(sharingBoardImgDtoList);
			
			return sharingBoardFormDto;
		}

	
	// 나눔완료 게시글 상세페이지 데이터 가져오기
	@Transactional(readOnly = true)
	public SharingBoardDto getCompletePost(Long sharingBoardId) {

		SharingBoard sharingBoard = sharingBoardRepository.findById(sharingBoardId).orElseThrow(EntityNotFoundException::new);
		SharingBoardDto sharingBoardDto = SharingBoardDto.of(sharingBoard);
		
		String sharedPostNickname = sharingBoard.getSharing().getMember().getNickName();
		sharingBoardDto.setSharing_member(sharedPostNickname);

		Story story = storyRepository.findBySharingIdAndChooseYn(sharingBoard.getSharing().getId(), "Y");
		String chooseYStory = story.getMember().getNickName();
		sharingBoardDto.setStory_member(chooseYStory);

		return sharingBoardDto;
	}

	// 나눔완료 게시글 여러 댓글들 보여줌
	@Transactional(readOnly = true)
	public List<SharingBoardCommentDto> getBoardCommentList(Long id) {
		List<SharingBoardComment> sharingBoardCommentList = sharingBoardCommentRepository.findBySharingBoardId(id);
		List<SharingBoardCommentDto> sharingBoardCommentDtoList = new ArrayList<>();

		for (SharingBoardComment sharingBoardComment : sharingBoardCommentList) {
			SharingBoardCommentDto sharingBoardCommentDto = SharingBoardCommentDto.of(sharingBoardComment);

			String sharedWriteCommentMember = sharingBoardComment.getMember().getNickName();
			sharingBoardCommentDto.setComment_member(sharedWriteCommentMember);

			sharingBoardCommentDtoList.add(sharingBoardCommentDto);

		}
		return sharingBoardCommentDtoList;
	}

	// 댓글 작성
	@Transactional
	public Long insertComment(Long member_id, Long sharedBoard_id, String comment) {
		Member sharedCommentMember = memberRepository.findById(member_id).orElseThrow(EntityNotFoundException::new);
		;
		SharingBoard sharedCommentPost = sharingBoardRepository.findById(sharedBoard_id)
				.orElseThrow(EntityNotFoundException::new);
		;

		SharingBoardComment sharingBoardComment = new SharingBoardComment();
		sharingBoardComment.setMember(sharedCommentMember);
		sharingBoardComment.setSharingBoard(sharedCommentPost);
		sharingBoardComment.setComment(comment);
		sharingBoardComment.setRegTime(LocalDateTime.now());

		sharingBoardCommentRepository.save(sharingBoardComment);

		return sharingBoardComment.getId();
	}
	
	public SharingBoardComment getSharingBoardComment (Long id) {
		return sharingBoardCommentRepository.getReferenceById(id);
	}
	
	//현재 로그인 한 사용자와 댓글 작성한 사용자와 같은지 검사
	@Transactional(readOnly = true)
	public boolean validateComment(Long comment_id, String email) {
		Member curMember = memberRepository.findByEmail(email);
		SharingBoardComment sharingBoardComment = sharingBoardCommentRepository.findById(comment_id).orElseThrow(EntityNotFoundException::new);
		Member savedMember = sharingBoardComment.getMember();
		
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			return false;
		}
		return true;
	}
	
	//댓글 삭제
	public void deleteComment(Long comment_id) {
		SharingBoardComment sharingBoardComment = sharingBoardCommentRepository.findById(comment_id).orElseThrow(EntityNotFoundException::new);
		
		sharingBoardCommentRepository.delete(sharingBoardComment);
	}
	
	
	//나눔완료 게시글 작성
	public Long insertSharedBoardPost(SharingBoardFormDto sharingBoardFormDto, List<MultipartFile> sharingBoardImgFileList ) throws Exception {
		
		SharingBoard sharingBoard = sharingBoardFormDto.createSharedPost();
		
		Sharing sharing = sharingRepository.findById(sharingBoardFormDto.getSharing_id()).orElseThrow(EntityNotFoundException::new);
		sharingBoard.setSharing(sharing);
		sharingBoardRepository.save(sharingBoard);
		
		//이미지 등록
		for(int i=0; i<sharingBoardImgFileList.size(); i++) {
			SharingBoardImg sharingBoardImg = new SharingBoardImg();
			sharingBoardImg.setSharingBoard(sharingBoard);
			
			if(i==0) {
				sharingBoardImg.setRepimgYn("Y");
			}else {
				sharingBoardImg.setRepimgYn("N");
			}
			sharingBoardImgService.saveSharingBoardImg(sharingBoardImg, sharingBoardImgFileList.get(i));
		}
		return sharingBoard.getId();
	}
	
	//게시글 수정 페이지 보여줌
	public SharingBoardFormDto viewsharingBoardFormDto(Long id) {
		SharingBoard sharingBoard = sharingBoardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		SharingBoardFormDto sharingBoardFormDto = SharingBoardFormDto.of(sharingBoard);
		
		sharingBoardFormDto.setId(sharingBoard.getId());
		sharingBoardFormDto.setSharing_name(sharingBoard.getSharing().getName());
		sharingBoardFormDto.setSubject(sharingBoard.getSubject());
		sharingBoardFormDto.setContent(sharingBoard.getContent());
		
		List<SharingBoardImgDto> sharingBoardDtoImgDtoList = sharingBoardImgService.getSharingBoardImgs(id);
		List<Long> sharingBoardImgIdList = new ArrayList<>();
		
		for(SharingBoardImgDto sharingBoardImgDto : sharingBoardDtoImgDtoList) {
			sharingBoardImgIdList.add(sharingBoardImgDto.getId());
		}
		
		sharingBoardFormDto.setSharingBoardImgIds(sharingBoardImgIdList);
		sharingBoardFormDto.setSharingBoardImgDtoList(sharingBoardDtoImgDtoList);
		
		return sharingBoardFormDto;
	}
	
	//게시글 수정
	public void sharingBoardUpdate(SharingBoardFormDto sharingBoardFormDto, List<MultipartFile> sharingBoardImgFileList) throws Exception {
		SharingBoard sharingBoard = sharingBoardRepository.findById(sharingBoardFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		
		sharingBoard.updateSharingBoard(sharingBoardFormDto);
		
		List<Long> sharingBoardImgIds = sharingBoardFormDto.getSharingBoardImgIds();
		
		if(sharingBoardImgFileList.size() >= 0 && !sharingBoardImgFileList.get(0).isEmpty()) {
			sharingBoardImgService.deleteImgsBySharingBoardId(sharingBoardFormDto.getId());
			for(int i=0; i<sharingBoardImgFileList.size(); i++) {
				SharingBoardImg sharingBoardImg = new SharingBoardImg();
				sharingBoardImg.setSharingBoard(sharingBoard);
				
				sharingBoardImg.setRepimgYn(i == 0 ? "Y" : "N");
				sharingBoardImgService.saveSharingBoardImg(sharingBoardImg, sharingBoardImgFileList.get(i));
			}
		}
	}
	
	//게시글 삭제
	public void deleteSharingBoard(Long sharingBoardId) {
		
		List<SharingBoardComment> sharingBoardCommentList = sharingBoardCommentRepository.findBySharingBoardId(sharingBoardId);
		sharingBoardCommentRepository.deleteAll(sharingBoardCommentList);
		
		SharingBoard sharingBoard = sharingBoardRepository.findById(sharingBoardId)
				.orElseThrow(EntityNotFoundException::new);
		
		sharingBoardRepository.delete(sharingBoard);
	}

	
	
	
}
