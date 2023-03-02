package com.sharingdonation.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sharingdonation.dto.SharingBoardHeartDto;
import com.sharingdonation.dto.SharingHeartDto;
import com.sharingdonation.entity.SharingBoardHeart;
import com.sharingdonation.entity.SharingHeart;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.SharingBoardHeartRepository;
import com.sharingdonation.repository.SharingBoardRepository;
import com.sharingdonation.repository.SharingHeartRepository;
import com.sharingdonation.repository.SharingRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SharingHeartService {
	private final SharingHeartRepository sharingHeartRepo;
	private final SharingRepository sharingRepo;
	private final MemberRepository memberRepo;
	private final SharingBoardHeartRepository sharingBoardHeartRepository;
	private final SharingBoardRepository sharingBoardRepository;

	public SharingHeartDto getSharingHeartDto(Long sharingId) {
		Long memberId = 1L;
		SharingHeart sharingHeart = sharingHeartRepo.findBySharingIdAndMemberId(sharingId, memberId).orElse(null);
		if (sharingHeart == null)
			return null;
		else
			return SharingHeartDto.of(sharingHeart);
	}
	
	public Long getSharingHeartCount(Long sharingId) {
		return sharingHeartRepo.countBySharingId(sharingId);
	}
	
	public void toggleSharingHeart(Long sharingId) {
		Long memberId = 1L;
		SharingHeart sharingHeart = sharingHeartRepo.findBySharingIdAndMemberId(sharingId, memberId).orElse(null);
		if (sharingHeart == null) {
			SharingHeart newHeart = new SharingHeart();
			newHeart.setMember(memberRepo.findById(memberId).orElseThrow(EntityNotFoundException::new));
			newHeart.setSharing(sharingRepo.findById(sharingId).orElseThrow(EntityNotFoundException::new));
			newHeart.setRegTime(LocalDateTime.now());
			
			sharingHeartRepo.save(newHeart);
		} else {
			sharingHeartRepo.delete(sharingHeart);
		}
	}
	
	public SharingBoardHeartDto getSharingBoardHeartDto(Long sharingBoard_id) {
		Long member_id = 1L;
		SharingBoardHeart sharingBoardHeart = sharingBoardHeartRepository.findBySharingBoardIdAndMemberId(sharingBoard_id, member_id).orElse(null);
		if (sharingBoardHeart == null)
			return null;
		else
			return SharingBoardHeartDto.of(sharingBoardHeart);
		
	}
	
	public Long getSharingBoardHeartCount(Long sharingBoard_id) {
		return sharingBoardHeartRepository.countBySharingBoardId(sharingBoard_id);
	}
	
	public void toggleSharingBoardHeart(Long sharingBoard_id) {
		Long member_id = 1L;
		SharingBoardHeart sharingBoardHeart = sharingBoardHeartRepository.findBySharingBoardIdAndMemberId(sharingBoard_id, member_id).orElse(null);
		if(sharingBoardHeart == null) {
			SharingBoardHeart newSharingBoardHeart = new SharingBoardHeart();
			newSharingBoardHeart.setMember(memberRepo.findById(member_id).orElseThrow(EntityNotFoundException::new));
			newSharingBoardHeart.setSharingBoard(sharingBoardRepository.findById(sharingBoard_id).orElseThrow(EntityNotFoundException::new));
			newSharingBoardHeart.setRegTime(LocalDateTime.now());
			
			sharingBoardHeartRepository.save(newSharingBoardHeart);
		}else {
			sharingBoardHeartRepository.delete(sharingBoardHeart);
		}
	}
}
