package com.sharingdonation.service;


import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.DonationImgDto;
import com.sharingdonation.dto.SharingImgDto;
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
		System.out.println("saveDonationImg");
		if(!StringUtils.isEmpty(oriImgName)) {
			System.out.println("-oriImgName-");
			imgName = fileService.uploadFile(donationImgLocation, oriImgName, donationImgFile.getBytes());
			imgUrl = "/imgs/donation/" + imgName;
			System.out.println("-updateFile-");
		}
		
		donationImg.updateDonationImg(imgName, oriImgName, imgUrl);
		System.out.println("-updateDonationImg-");
		donationImgRepository.save(donationImg);
	}
	
	public void updateDonationImg(Long donationImgId, MultipartFile donationImgFile) throws Exception {
		if(!donationImgFile.isEmpty()) {
			DonationImg savedDonationImg = donationImgRepository.findById(donationImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			
			//기존 이미지 파일 삭제
			if(!StringUtils.isEmpty(savedDonationImg.getImgName())) {
				fileService.deleteFile(donationImgLocation + "/" + savedDonationImg.getImgName());
			}
			
			String oriImgName = donationImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(donationImgLocation, oriImgName, donationImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			savedDonationImg.updateDonationImg(imgName, oriImgName, imgUrl);
		}
	}
	public void deleteImgsByDonationId(Long donationId) {
		donationImgRepository.deleteAllByDonationId(donationId);
		donationImgRepository.flush();
	}
	
	public DonationImgDto getDonationImgDto(Long donationId) {
		List<DonationImgDto> donationImgDtoList = donationImgRepository.findByDonationId(donationId).stream().map(DonationImgDto::of).toList();
		List<DonationImgDto> filteredDtoList  = donationImgDtoList.stream().filter(s -> s.getRepimgYn().equals("Y")).toList();
		if (filteredDtoList.size() == 0)
			return null;
		else 
			return filteredDtoList.get(0);
	}
}
