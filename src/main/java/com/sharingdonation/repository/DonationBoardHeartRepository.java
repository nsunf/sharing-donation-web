package com.sharingdonation.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.DonationBoardHeart;


public interface DonationBoardHeartRepository extends JpaRepository<DonationBoardHeart, Long>{
	Optional<DonationBoardHeart> findByDonationBoardIdAndMemberId(Long donationBoardId, Long memberId);
	Long countByDonationBoardId(Long donationBoardId);
	
	Page<DonationBoardHeart> findByDonationBoardSubjectContainsOrderByRegTimeDesc(String subject, Pageable pageable);
	Page<DonationBoardHeart> findByMemberNickNameContainsOrderByRegTimeDesc(String nickname, Pageable pageable);
}
