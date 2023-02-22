package com.sharingdonation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sharingdonation.dto.DonationSearchDto;
import com.sharingdonation.dto.ListDonationDto;
import com.sharingdonation.entity.Donation;

public interface DonationRepositoryCustom {
	Page<Donation> getAdminDonationPage(DonationSearchDto donationSearchDto, Pageable pageable);
	
	Page<ListDonationDto> getListDonationPage(DonationSearchDto donationSearchDto, Pageable pageable);
}
