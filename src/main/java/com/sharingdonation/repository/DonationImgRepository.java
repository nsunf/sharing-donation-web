package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sharingdonation.entity.DonationImg;

@Repository
public interface DonationImgRepository extends JpaRepository<DonationImg, Long> {
	List<DonationImg> findByDonationIdOrderByIdAsc(Long donationId);
	
	DonationImg findByDonationIdAndRepimgYn(Long donationId, String repimgYn);
	
	List<DonationImg> findByDonationId(Long donationId);
	
	void deleteAllByDonationId(Long donationId);
}
