package com.sharingdonation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sharingdonation.dto.DonationBoardDto;
import com.sharingdonation.entity.DonationBoard;

public interface DonationBoardRepositoryCustom {
	Page<DonationBoard> getAdminDonationBoardPage(Pageable pageable);
	Page<DonationBoardDto> getDonationBoardPage(Pageable pageable);
}
