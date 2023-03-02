package com.sharingdonation.service;

import javax.persistence.EntityNotFoundException;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.entity.SharingBoardImg;
import com.sharingdonation.repository.SharingBoardImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SharingBoardImgService {
	
	@Value("${sharingBoardImgLocation}")
	private String sharingBoardImgLocation;
	
	private final SharingBoardImgRepository sharingBoardImgRepository;
	
	private final FileService fileService;
	
	//나눔완료 게시판 이미지 저장
	public void saveSharingBoardImg(SharingBoardImg sharingBoardImg, MultipartFile sharingBoardImgFile) throws Exception {
		String oriImgName = sharingBoardImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(sharingBoardImgLocation, oriImgName, sharingBoardImgFile.getBytes());
			imgUrl = "/sharedimages/sharedBoard/"+imgName;
		}
		
		sharingBoardImg.updateSharingBoardImg(imgName, oriImgName, imgUrl);
		sharingBoardImgRepository.save(sharingBoardImg);
	}
	
	public void updateSharingBoardImg(Long sharingBoardImgId, MultipartFile sharingBoardImgFile) throws Exception {
		if(!sharingBoardImgFile.isEmpty()) {
			SharingBoardImg savedSharingBoardImg = sharingBoardImgRepository.findById(sharingBoardImgId).orElseThrow(EntityNotFoundException::new);
			
			//기존 이미지 파일 삭제
			if(!StringUtils.isEmpty(savedSharingBoardImg.getImgName())) {
				fileService.deleteFile(sharingBoardImgLocation+"/"+savedSharingBoardImg.getImgName());
			}
			
			//수정된 이미지 파일 업로드
			String oriImgName = sharingBoardImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(sharingBoardImgLocation, oriImgName, sharingBoardImgFile.getBytes());
			String imgUrl = "/sharedimages/sharedBoard"+imgName;
			
			savedSharingBoardImg.updateSharingBoardImg(oriImgName, imgName, imgUrl);
		}
	}
}
