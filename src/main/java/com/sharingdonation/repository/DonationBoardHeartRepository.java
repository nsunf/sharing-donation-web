package com.sharingdonation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.DonationBoardHeart;


public interface DonationBoardHeartRepository extends JpaRepository<DonationBoardHeart, Long>{
	Optional<DonationBoardHeart> findByDonationBoardIdAndMemberId(Long donationBoardId, Long memberId);
	Long countByDonationBoardId(Long donationBoardId);
}
