package com.sharingdonation.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.DonationHeart;

public interface DonationHeartRepository extends JpaRepository<DonationHeart, Long>  {
	Optional<DonationHeart> findByDonationIdAndMemberId(Long donationId, Long memberId);
	Long countByDonationId(Long donationId);
<<<<<<< HEAD
	void deleteAllByDonationId(Long donationId);
=======
	
	Page<DonationHeart> findByDonationSubjectContainsOrderByRegTimeDesc(String subject, Pageable pageable);
	Page<DonationHeart> findByMemberNickNameContainsOrderByRegTimeDesc(String name, Pageable pageable);
>>>>>>> branch 'development' of https://github.com/nsunf/sharing-donation-web.git
}
