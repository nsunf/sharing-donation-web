package com.sharingdonation.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharingdonation.dto.DonationBoardHeartDto;
import com.sharingdonation.entity.DonationBoardHeart;
import com.sharingdonation.repository.DonationBoardHeartRepository;
import com.sharingdonation.repository.DonationBoardRepository;
import com.sharingdonation.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DonationBoardHeartService {
	private final MemberRepository memberRepository;
	private final DonationBoardHeartRepository donationBoardHeartRepository;
	private final DonationBoardRepository donationBoardRepository;
	
	public DonationBoardHeartDto getDonationBoardHeartDto(Long donationBoardId) {
		Long memberId = 1L;
		DonationBoardHeart donationBoardHeart = donationBoardHeartRepository.findByDonationBoardIdAndMemberId(donationBoardId, memberId).orElse(null);
		if(donationBoardHeart == null) {
			return null;
		}else {
			return DonationBoardHeartDto.of(donationBoardHeart);
		}
	}
	
	
	
	public Long getDonationBoardHeartCount(Long donationBoardId) {
//		System.out.println("getDonationBoardHeartCount");
//		Long d = donationBoardHeartRepository.countByDonationBoardId(donationBoardId);
//		System.out.println("d" + d);
		return donationBoardHeartRepository.countByDonationBoardId(donationBoardId);
	}
	
	
	public void toggleDonationBoardHeart(Long donationBoardId) {
		Long memberId = 1L;
		DonationBoardHeart donationBoardHeart = donationBoardHeartRepository.findByDonationBoardIdAndMemberId(donationBoardId, memberId).orElse(null);
		if(donationBoardHeart == null) {
			DonationBoardHeart newHeart = new DonationBoardHeart();
			newHeart.setMember(memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new));
			newHeart.setDonationBoard(donationBoardRepository.findById(donationBoardId).orElseThrow(EntityNotFoundException::new));
			newHeart.setRegTime(LocalDateTime.now());
			
			donationBoardHeartRepository.save(newHeart);
		}else {
			donationBoardHeartRepository.delete(donationBoardHeart);
		}
		
		
	}
	
	
}
