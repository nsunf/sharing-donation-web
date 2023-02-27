package com.sharingdonation.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.sharingdonation.dto.SharingImgDto;
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
			imgUrl = "/imgs/sharing/" + imgName;
		}
		
		sharingImg.setOriImgName(oriImgName);
		sharingImg.setImgName(imgName);
		sharingImg.setImgUrl(imgUrl);
		sharingImgRepo.save(sharingImg);
	}
	
	public void updateImg(Long sharingImgId, MultipartFile sharingImgFile) throws Exception {
		if (!sharingImgFile.isEmpty()) {
			SharingImg savedItemImg = sharingImgRepo.findById(sharingImgId).orElseThrow(EntityNotFoundException::new);
			
			if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
				fileService.deleteFile(sharingImgLocation + "/" + savedItemImg.getImgName());
			}
			
			String oriImgName = sharingImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(sharingImgLocation, oriImgName, sharingImgFile.getBytes());
			String imgUrl = "/imgs/sharing/" + imgName;
			
			savedItemImg.setOriImgName(oriImgName);
			savedItemImg.setImgName(imgName);
			savedItemImg.setImgUrl(imgUrl);
		}
	}
	
	public void deleteImgsBySharingId(Long sharingId) {
		sharingImgRepo.deleteAllBySharingId(sharingId);
	}
	
	public SharingImgDto getSharingImgDto(Long sharingId) {
		List<SharingImgDto> sharingImgDtoList = sharingImgRepo.findBySharingId(sharingId).stream().map(SharingImgDto::of).toList();
		List<SharingImgDto> filteredDtoList  = sharingImgDtoList.stream().filter(s -> s.getRepImgYn().equals("Y")).toList();
		if (filteredDtoList.size() == 0)
			return sharingImgDtoList.get(0);
		else 
			return filteredDtoList.get(0);
	}
	
	public List<SharingImgDto> getSharingImgDtoList(Long sharingId) {
		List<SharingImgDto> sharingImgDtoList = sharingImgRepo.findBySharingId(sharingId).stream().map(SharingImgDto::of).toList();
		System.out.println("---> " + sharingImgDtoList.size());
		return sharingImgDtoList;
	}
}
