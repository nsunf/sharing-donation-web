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

import com.sharingdonation.dto.DonationBoardCommentDto;
import com.sharingdonation.dto.DonationBoardDto;
import com.sharingdonation.dto.DonationBoardFormDto;
import com.sharingdonation.dto.DonationBoardImgDto;
import com.sharingdonation.dto.DonationBoardSearchDto;
import com.sharingdonation.dto.DonationBoardSelectDto;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationBoard;
import com.sharingdonation.entity.DonationBoardComment;
import com.sharingdonation.entity.DonationBoardHeart;
import com.sharingdonation.entity.DonationBoardImg;
import com.sharingdonation.entity.Member;
import com.sharingdonation.repository.DonationBoardCommentRepository;
import com.sharingdonation.repository.DonationBoardHeartRepository;
import com.sharingdonation.repository.DonationBoardImgRepository;
import com.sharingdonation.repository.DonationBoardRepository;
import com.sharingdonation.repository.DonationRepository;
import com.sharingdonation.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationBoardService {
	private final DonationBoardRepository donationBoardRepository;
	private final DonationBoardImgService donationBoardImgService;
	private final DonationBoardImgRepository donationBoardImgRepository;
	private final DonationRepository donationRepository;
	private final DonationBoardCommentRepository donationBoardCommentRepository;
	private final MemberRepository memberRepository;
	private final DonationBoardHeartRepository donationBoardHeartRepository;
	
	
	//donation data
	public List<DonationBoardSelectDto> getDonationBorardSelect(){
		List<Donation> donations = donationRepository.findByDone("Y");
		
		List<DonationBoardSelectDto> donationBoardSelectDtos = new ArrayList<>();
		
		for(Donation donation : donations) {
			DonationBoardSelectDto donationBoardSelectDto = DonationBoardSelectDto.of(donation);
			donationBoardSelectDtos.add(donationBoardSelectDto);
		}
		return donationBoardSelectDtos;
		
	}
	
	
	
	
	
	//create donation board
	public Long SaveDonationBoard(DonationBoardFormDto donationBoardFormDto, List<MultipartFile> donationBoardImgFileList) throws Exception{
		DonationBoard donationBoard = donationBoardFormDto.createDonationBoard();
		
		Donation donation = donationRepository.findById(donationBoardFormDto.getDonationId()).orElseThrow(EntityNotFoundException::new);
		donationBoard.setDonation(donation);

		donationBoardRepository.save(donationBoard);
		
		//이미지 등록
		for(int i=0; i<donationBoardImgFileList.size(); i++) {
			DonationBoardImg donationBoardImg = new DonationBoardImg();
			donationBoardImg.setDonationBoard(donationBoard);
			
			if(i == 0) {
				donationBoardImg.setRepimgYn("Y");
			}else {
				donationBoardImg.setRepimgYn("N");
			}
			
			donationBoardImgService.saveDonationBoardImg(donationBoardImg, donationBoardImgFileList.get(i));
		}
		
		return donationBoard.getId();
	}
	
	
	
	//donation board list
	@Transactional(readOnly = true)
	public Page<DonationBoard> getAdminDonationBoardDtoPage(DonationBoardSearchDto donationBoardSearchDto, Pageable pageable){
		return donationBoardRepository.getAdminDonationBoardPage(donationBoardSearchDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<DonationBoardDto> getDonationBoardDtoPage(DonationBoardSearchDto donationBoardSearchDto, Pageable pageable){
		return donationBoardRepository.getDonationBoardPage(donationBoardSearchDto, pageable);
	}


 
	//get donation board detail
	@Transactional(readOnly = true)
	public DonationBoardFormDto getdonationBoardDetail(Long donationBoardId) {
		List<DonationBoardImg> donationBoardImgList = donationBoardImgRepository.findByDonationBoardIdOrderByIdAsc(donationBoardId);
		List<DonationBoardImgDto> donationBoardImgDtoList = new ArrayList<>();
		
		for(DonationBoardImg donationBoardImg : donationBoardImgList) {
			DonationBoardImgDto donationBoardImgDto = DonationBoardImgDto.of(donationBoardImg);
			
			donationBoardImgDtoList.add(donationBoardImgDto);
		}
		
				Long heartCount =  donationBoardHeartRepository.countByDonationBoardId(donationBoardId);
		
		
		//2. donation board테이블에 있는 데이터를 가져온다.
				DonationBoard donationBoard = donationBoardRepository.findById(donationBoardId)
											 .orElseThrow(EntityNotFoundException::new);
//				System.out.println("DonationBoardFormDto donationBoard");
				
				//엔티티 객체 -> dto객체로 변환
				DonationBoardFormDto donationBoardFormDto = DonationBoardFormDto.of(donationBoard);
//				System.out.println(" DonationBoardFormDto donationBoardFormDto");
				
				//이미지 정보를 넣어준다.
				donationBoardFormDto.setHeartCount(heartCount);
				donationBoardFormDto.setDonationBoardImgDtoList(donationBoardImgDtoList);
				
				
				return donationBoardFormDto;
		
	}
	
	
	
	//댓글 작성
			public Long insertComment(Long memberId, Long donationBoardId, String comment){
				Member donatedCommentMember = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
				DonationBoard donatedCommentPost = donationBoardRepository.findById(donationBoardId).orElseThrow(EntityNotFoundException::new);
				
				DonationBoardComment donationBoardComment = new DonationBoardComment();
				donationBoardComment.setDonationBoard(donatedCommentPost);
				donationBoardComment.setMember(donatedCommentMember);
				donationBoardComment.setRegTime(LocalDateTime.now());
				donationBoardComment.setComment(comment);
				
				donationBoardCommentRepository.save(donationBoardComment);
				
				return donationBoardComment.getId();
			}
	
			
			
	// 기부완료 게시글 여러 댓글들 보여줌
		@Transactional
		public List<DonationBoardCommentDto> getBoardCommentList(Long id) {
			System.out.println("확인 : start  "+id);
			List<DonationBoardComment> donationBoardCommentList = donationBoardCommentRepository.findByDonationBoardId(id);
			System.out.println("확인 : start1" + donationBoardCommentList);
			List<DonationBoardCommentDto> donationBoardCommentDtoList = new ArrayList<>();
			System.out.println("확인 : start2"+ donationBoardCommentDtoList);
			
			
			for (DonationBoardComment donationBoardComment : donationBoardCommentList) {
				DonationBoardCommentDto donationBoardCommentDto = DonationBoardCommentDto.of(donationBoardComment);
//				System.out.println("확인 : start3");
				
				//String donatedWriteCommentMember = donationBoardComment.getMember().getNickName(); 修改前直接用这个方法得到昵称，但是要得到两个的话，在会员这里截止，然后分别得到两个值。
				Member donatedWriteCommentMember = donationBoardComment.getMember();
				
//				System.out.println("확인 : start4"+donationBoardComment.getMember().getNickName());
				
				donationBoardCommentDto.setCommentMember(donatedWriteCommentMember.getNickName());
//				System.out.println("확인 : start5"+donationBoardCommentDto.getComment());
				donationBoardCommentDto.setCommentEmail(donatedWriteCommentMember.getEmail());
				
				donationBoardCommentDtoList.add(donationBoardCommentDto);

			}
			return donationBoardCommentDtoList;
		}
		
		
		//댓글 삭제 
		public void deleteComment(Long commentId) {
			donationBoardCommentRepository.deleteById(commentId);
					
		}

		
		//donationBoard modify
		public Long donationBoardUpdate(DonationBoardFormDto donationBoardFormDto, List<MultipartFile> donationBoardImgFileList)  throws Exception {
			System.out.println("donationBoardFormDto.getId() ==== " + donationBoardFormDto.getId());
			DonationBoard donationBoard = donationBoardRepository.findById(donationBoardFormDto.getId())
					.orElseThrow(EntityNotFoundException::new);
			
			donationBoard.updateDonationBoard(donationBoardFormDto);
			System.out.println(" service donationBoardUpdate updateDonationBoard"); 
//			List<Long> donationBoardImgIds = donationBoardFormDto.getDonationBoardImgIds();
			 System.out.println(" service donationBoardUpdate donationBoardImgIds");
			 if(donationBoardImgFileList.size() >= 0 && !donationBoardImgFileList.get(0).isEmpty()) {
				 donationBoardImgService.deleteImgsByDonationBoardId(donationBoardFormDto.getId());
				 
				for(int i=0; i<donationBoardImgFileList.size(); i++) {
					System.out.println(" service donationBoardUpdate donationBoardImgFileList.size() index == " + i);
					DonationBoardImg donationBoardImg = new DonationBoardImg();
					donationBoardImg.setDonationBoard(donationBoard);
					donationBoardImg.setRepimgYn(i == 0 ? "Y" : "N");
					donationBoardImgService.saveDonationBoardImg(donationBoardImg, donationBoardImgFileList.get(i));
//					donationBoardImgService.updateDonationBoardImg(donationBoardImgIds.get(i), donationBoardImgFileList.get(i));
					System.out.println(" service donationBoardUpdate updateDonationBoardImg");
				}
			 }
			
			return donationBoard.getId();
			
			
			
			
		}


		//delete
		public void deleteDonationBoard(Long donationBoardId) {
			DonationBoard donationBoard = donationBoardRepository.findById(donationBoardId)
					.orElseThrow(EntityNotFoundException::new);
			
			donationBoardRepository.delete(donationBoard);
			
			DonationBoardComment donationBoardComment = donationBoardCommentRepository.findById(donationBoardId)
					.orElseThrow(EntityNotFoundException::new);
			
			donationBoardCommentRepository.delete(donationBoardComment);
			
			DonationBoardHeart donationBoardHeart = donationBoardHeartRepository.findById(donationBoardId)
					.orElseThrow(EntityNotFoundException::new);
			donationBoardHeartRepository.delete(donationBoardHeart);
		}




		








		
		
		
}
