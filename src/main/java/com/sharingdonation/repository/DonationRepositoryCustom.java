package com.sharingdonation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sharingdonation.dto.DonationDto;
import com.sharingdonation.dto.SearchDto;
import com.sharingdonation.dto.ListDonationDto;
import com.sharingdonation.entity.Donation;

public interface DonationRepositoryCustom {
	Page<DonationDto> getAdminListDonationPage(SearchDto searchDto, Pageable pageable);
	
	Page<ListDonationDto> getListDonationPage(SearchDto searchDto, Pageable pageable);
}
