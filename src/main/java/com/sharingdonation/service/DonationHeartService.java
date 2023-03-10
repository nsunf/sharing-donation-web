package com.sharingdonation.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sharingdonation.dto.DonationHeartDto;
import com.sharingdonation.entity.DonationHeart;
import com.sharingdonation.repository.DonationHeartRepository;
import com.sharingdonation.repository.DonationRepository;
import com.sharingdonation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DonationHeartService {
	private final DonationHeartRepository donationHeartRepository;
	private final DonationRepository donationRepository;
	private final MemberRepository memberRepository;

	public DonationHeartDto getDonationHeartDto(Long memberId, Long donationId) {
		DonationHeart donationHeart = donationHeartRepository.findByDonationIdAndMemberId(donationId, memberId).orElse(null);
		if (donationHeart == null)
			return null;
		else
			return DonationHeartDto.of(donationHeart);
	}
	
	public Long getDonationHeartCount(Long donationId) {
		return donationHeartRepository.countByDonationId(donationId);
	}
	
	public void toggleDonationHeart(Long memberId, Long donationId) {
		DonationHeart donationHeart = donationHeartRepository.findByDonationIdAndMemberId(donationId, memberId).orElse(null);
		if (donationHeart == null) {
			DonationHeart newHeart = new DonationHeart();
			newHeart.setMember(memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new));
			newHeart.setDonation(donationRepository.findById(donationId).orElseThrow(EntityNotFoundException::new));
			newHeart.setRegTime(LocalDateTime.now());
			
			donationHeartRepository.save(newHeart);
		} else {
			donationHeartRepository.delete(donationHeart);
		}
	}
}
