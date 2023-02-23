package com.sharingdonation.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.sharingdonation.entity.SharingImg;
import com.sharingdonation.repository.SharingImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SharingImgService {
	@Value("${sharingImgLocation}")
	private String sharingImgLocation;
	
	private final SharingImgRepository sharingImgRepo;
	
	private final FileService fileService;
	
	public void saveImg(SharingImg sharingImg, MultipartFile sharingImgFile) throws Exception {
		String oriImgName = sharingImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if (!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(sharingImgLocation, oriImgName, sharingImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}
		
		sharingImg.setOriImgName(oriImgName);
		sharingImg.setImgName(imgName);
		sharingImg.setImgUrl(imgUrl);
		sharingImgRepo.save(sharingImg);
	}
	
	public void updateItemImg(Long sharingImgId, MultipartFile sharingImgFile) throws Exception {
		if (!sharingImgFile.isEmpty()) {
			SharingImg savedItemImg = sharingImgRepo.findById(sharingImgId).orElseThrow(EntityNotFoundException::new);
			
			if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
				fileService.deleteFile(sharingImgLocation + "/" + savedItemImg.getImgName());
			}
			
			String oriImgName = sharingImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(sharingImgLocation, oriImgName, sharingImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			savedItemImg.setOriImgName(oriImgName);
			savedItemImg.setImgName(imgName);
			savedItemImg.setImgUrl(imgUrl);
		}
	}
}
