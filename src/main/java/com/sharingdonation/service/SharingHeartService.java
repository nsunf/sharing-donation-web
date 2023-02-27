package com.sharingdonation.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sharingdonation.dto.SharingHeartDto;
import com.sharingdonation.entity.SharingHeart;
import com.sharingdonation.repository.MemberRepository;
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
}
