package com.sharingdonation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationHeart extends JpaRepository<DonationHeart, Long>  {
	long countByDonationId(Long donationId);
}
