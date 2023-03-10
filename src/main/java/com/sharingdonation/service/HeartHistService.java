package com.sharingdonation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sharingdonation.dto.HeartAdminSearchDto;
import com.sharingdonation.dto.HeartHistDto;
import com.sharingdonation.repository.DonationBoardHeartRepository;
import com.sharingdonation.repository.DonationHeartRepository;
import com.sharingdonation.repository.SharingBoardHeartRepository;
import com.sharingdonation.repository.SharingHeartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeartHistService {
	private final SharingHeartRepository sharingHeartRepo;
	private final SharingBoardHeartRepository sharingBoardHeartRepo;
	private final DonationHeartRepository donationHeartRepo;
	private final DonationBoardHeartRepository donationBoardHeartRepo;

	public Page<HeartHistDto> getHeartDtoList(HeartAdminSearchDto searchDto, Pageable pageable) {
		Page<HeartHistDto> result = new PageImpl<>(new ArrayList<>());
		String filter = searchDto.getFilter();
		String searchTerm = searchDto.getSearch();
		
		switch (searchDto.getType()) {
		case SHARING:
			if (filter.equals("title"))
				result = sharingHeartRepo.findBySharingNameContainsOrderByRegTimeDesc(searchTerm, pageable)
					.map(HeartHistDto::valueOf);
			else 
				result = sharingHeartRepo.findByMemberNickNameContainsOrderByRegTimeDesc(searchTerm, pageable)
					.map(HeartHistDto::valueOf);
			 break;
		case SHARING_BOARD:
			if (filter.equals("title"))
				result = sharingBoardHeartRepo.findBySharingBoardSubjectContainsOrderByRegTimeDesc(searchTerm, pageable)
					.map(HeartHistDto::valueOf);
			else 
				result = sharingBoardHeartRepo.findByMemberNickNameContainsOrderByRegTimeDesc(searchTerm, pageable)
					.map(HeartHistDto::valueOf);
			break;
		case DONATION:
			if (filter.equals("title"))
				result = donationHeartRepo.findByDonationSubjectContainsOrderByRegTimeDesc(searchTerm, pageable)
					.map(HeartHistDto::valueOf);
			else 
				result = donationHeartRepo.findByMemberNickNameContainsOrderByRegTimeDesc(searchTerm, pageable)
					.map(HeartHistDto::valueOf);
			break;
		case DONATION_BOARD:
			if (filter.equals("title"))
				result = donationBoardHeartRepo.findByDonationBoardSubjectContainsOrderByRegTimeDesc(searchTerm, pageable)
					.map(HeartHistDto::valueOf);
			else 
				result = donationBoardHeartRepo.findByMemberNickNameContainsOrderByRegTimeDesc(searchTerm, pageable)
					.map(HeartHistDto::valueOf);
		default:
			break;
		}

		return result;
	}
}
