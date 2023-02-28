package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.DonationBoardImg;

public interface DonationBoardImgRepository extends JpaRepository<DonationBoardImg, Long>{
	 List<DonationBoardImg> findByDonationBoardIdOrderByIdAsc(Long donationBoardId);
}
