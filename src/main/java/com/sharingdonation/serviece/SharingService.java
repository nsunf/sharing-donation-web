package com.sharingdonation.serviece;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.SharingFormDto;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.SharingImg;
import com.sharingdonation.repository.SharingRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SharingService {
	
	private final SharingRepository sharingRepo;
	private final SharingImgService sharingImgService;

	public void insertDummySharings() {
		for (int i = 0; i < 10; i++) {
			Sharing sharing = new Sharing();
			sharing.setName("나눔 상품명--" + i);
		}
	}
	
	public void saveSharing(SharingFormDto sharingFormDto, List<MultipartFile> sharingImgFileList) throws Exception {
		Sharing sharing = sharingFormDto.createSharing();
		sharingRepo.save(sharing);
		
		for (int i = 0; i < sharingImgFileList.size(); i++) {
			SharingImg sharingImg = new SharingImg();
			sharingImg.setSharing(sharing);
			
			sharingImg.setRepimgYn(i == 0 ? "Y" : "N");
			sharingImgService.saveImg(sharingImg, sharingImgFileList.get(i));
		}
	}

}
