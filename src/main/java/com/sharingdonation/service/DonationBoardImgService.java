package com.sharingdonation.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.sharingdonation.entity.DonationBoardImg;
import com.sharingdonation.repository.DonationBoardImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationBoardImgService {
	@Value("${donationBoardImgLocation}")
	private String donationBoardImgLocation;
	
	private final DonationBoardImgRepository donationBoardImgRepository;
	
	private final FileService fileService;
	
	//donationBoardImg save method
	public void saveDonationBoardImg(DonationBoardImg donationBoardImg, MultipartFile donationBoardImgFile) throws Exception{
		String oriImgName = donationBoardImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(donationBoardImgLocation, oriImgName, donationBoardImgFile.getBytes());
			imgUrl = "/donatedimages/donatedBoard/" + imgName;
		}
		
		donationBoardImg.updateDonationBoardImg(imgName, oriImgName, imgUrl);
		donationBoardImgRepository.save(donationBoardImg);
	}
	
	public void updateDonationBoardImg(Long donationBoardImgId, MultipartFile donationBoardImgFile) throws Exception {
		if(!donationBoardImgFile.isEmpty()) {
			DonationBoardImg savedDonationBoardImg = donationBoardImgRepository.findById(donationBoardImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			//기존 이미지 파일 삭
			if(!StringUtils.isEmpty(savedDonationBoardImg.getImgName())) {
				fileService.deleteFile(donationBoardImgLocation + "/" + savedDonationBoardImg.getImgName());
			}
			
			//수정된 이미지 파일 업로드
			String oriImgName = donationBoardImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(donationBoardImgLocation, oriImgName, donationBoardImgFile.getBytes());
			String imgUrl = "/donatedimages/donatedBoard" + imgName;
			
			savedDonationBoardImg.updateDonationBoardImg(oriImgName, imgName, imgUrl);
			
		}
	}

	public void deleteDonationBoardImg(Long donationBoardId) throws Exception{
		DonationBoardImg deleteDonationBoardImg = donationBoardImgRepository.findById(donationBoardId)
				.orElseThrow(EntityNotFoundException::new);
		
		if(!StringUtils.isEmpty(deleteDonationBoardImg.getImgName())) {
			fileService.deleteFile(donationBoardImgLocation + "/" + deleteDonationBoardImg.getImgName());
		}
		
		
		donationBoardImgRepository.delete(deleteDonationBoardImg);
	}
}
