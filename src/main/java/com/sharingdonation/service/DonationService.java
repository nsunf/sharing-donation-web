package com.sharingdonation.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.dto.DonationImgDto;
import com.sharingdonation.dto.DonationSearchDto;
import com.sharingdonation.dto.ListDonationDto;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationImg;
import com.sharingdonation.repository.DonationImgRepository;
import com.sharingdonation.repository.DonationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {
	private final DonationRepository donationRepository;
	private final DonationImgService donationImgService;
	private final DonationImgRepository donationImgRepository;
	
	public Long saveDonation(DonationFormDto donationFormDto, List<MultipartFile> donationImgFileList) throws Exception {
		Donation donation = donationFormDto.createDonation();
		donationRepository.save(donation);
		
		for(int i = 0; i < donationImgFileList.size(); i++) {
			DonationImg donationImg = new DonationImg();
			donationImg.setDonation(donation);
			
			if(i == 0) {
				donationImg.setRepimgYn("Y");
			} else {
				donationImg.setRepimgYn("N");
			}
			donationImgService.saveDonationImg(donationImg, donationImgFileList.get(i));
		}
		return donation.getId();
	}
	
	@Transactional(readOnly = true)
	public DonationFormDto getDonationDtl(Long donationId) {
		List<DonationImg> donationImgList = donationImgRepository.findByDonationIdOrderByIdAsc(donationId);
		List<DonationImgDto> donationImgDtoList = new ArrayList<>();
		
		for(DonationImg donationImg : donationImgList) {
			DonationImgDto donationImgDto = DonationImgDto.of(donationImg);
			donationImgDtoList.add(donationImgDto);
		}
		
		Donation donation = donationRepository.findById(donationId)
				.orElseThrow(EntityNotFoundException::new);
		
		DonationFormDto donationFormDto = DonationFormDto.of(donation);
		
		donationFormDto.setDonationImgDtoList(donationImgDtoList);
		
		return donationFormDto;
	}
	
	public Long updateDonation(DonationFormDto donationFormDto, List<MultipartFile> donationImgFileList) throws Exception {
		Donation donation = donationRepository.findById(donationFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);
				
		donation.updateDonation(donationFormDto);
		
		List<Long> donationImgIds = donationFormDto.getDonateionImgIds();
		
		for(int i = 0; i<donationImgFileList.size(); i++) {
			donationImgService.updateDonationImg(donationImgIds.get(i), donationImgFileList.get(i));
		}
		
		return donation.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<Donation> getAdminListDonationPage(DonationSearchDto donationSearchDto, Pageable pageable) {
		return donationRepository.getAdminListDonationPage(donationSearchDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<ListDonationDto> getListDonationPage(DonationSearchDto donationSearchDto, Pageable pageable) {
		Page<ListDonationDto> donationList = donationRepository.getListDonationPage(donationSearchDto, pageable);
		for(ListDonationDto donation : donationList) {
			int pointPer = 0 ;
			
			if(donation.getPointSum() != 0 ) {
				pointPer = (int)(donation.getPointSum() / donation.getGoalPoint());
			}
			donation.setPointPer(pointPer);
			
			System.out.println(donation.getId() + ":"+ donation.getPointSum() +":"+ donation.getGoalPoint() +":"+ donation.getPointPer());
		}
		return donationList;
	}
}
