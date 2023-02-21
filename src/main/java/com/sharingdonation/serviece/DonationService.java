package com.sharingdonation.serviece;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.DonationFormDto;
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
	
	public Long SaveDonation(DonationFormDto donationFormDto, List<MultipartFile> donationImgFileList) throws Exception {
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
}
