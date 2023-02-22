package com.sharingdonation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.DonationHeart;

public interface DonationHeartRepository extends JpaRepository<DonationHeart, Long>  {
	long countByDonationId(Long donationId);
}
