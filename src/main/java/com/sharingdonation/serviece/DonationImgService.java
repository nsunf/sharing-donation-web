package com.sharingdonation.serviece;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.entity.DonationImg;
import com.sharingdonation.repository.DonationImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationImgService {
	@Value("${itemImgLocation}")
	private String donationImgLocation;
	
	private final DonationImgRepository donationImgRepository;
	
	private final FileService fileService;
	
	public void saveDonationImg(DonationImg donationImg, MultipartFile donationImgFile) throws Exception {
		String oriImgName = donationImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(donationImgLocation, oriImgName, donationImgFile.getBytes());
			imgUrl = "" + imgName;
		}
		
		donationImg.updateDonationImg(imgName, oriImgName, imgUrl);
		donationImgRepository.save(donationImg);
	}
	
	public void updateDonationImg(Long donationImgId, MultipartFile donationImgFile) throws Exception {
		if(!donationImgFile.isEmpty()) {
			DonationImg savedDonationImg = donationImgRepository.findById(donationImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			if(!StringUtils.isEmpty(savedDonationImg.getImgName())) {
				fileService.deleteFile(donationImgLocation + "/" + savedDonationImg.getImgName());
			}
			
			String oriImgName = donationImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(donationImgLocation, oriImgName, donationImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			savedDonationImg.updateDonationImg(imgName, oriImgName, imgUrl);
		}
	}
}
