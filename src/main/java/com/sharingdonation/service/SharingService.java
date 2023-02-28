package com.sharingdonation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.SharingDto;
import com.sharingdonation.dto.SharingFormDto;
import com.sharingdonation.dto.SharingImgDto;
import com.sharingdonation.entity.Area;
import com.sharingdonation.entity.Category;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.SharingImg;
import com.sharingdonation.repository.AreaRepository;
import com.sharingdonation.repository.CategoryRepository;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.SharingRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SharingService {
	
	private final SharingRepository sharingRepo;
	private final SharingImgService sharingImgService;
	private final SharingHeartService sharingHeartService;
	private final CategoryRepository categoryRepo;
	private final AreaRepository areaRepo;
	private final MemberRepository memberRepo;

	public SharingDto getSharingDto(Long sharingId) {
		Sharing sharing = sharingRepo.findById(sharingId).orElseThrow(EntityNotFoundException::new);
		
		return SharingDto.valueOf(sharing);
	}

	public Page<SharingDto> getSharingDtoList(String search, String areaName, String catName, Pageable pageable) {
		Page<Sharing> sharingList = null;
		String _search = search == null ? "" : search;
		if (catName == null) {
			sharingList = sharingRepo.findAllByDetailContainsAndConfirmYnAndDelYnAndAreaGugunOrderByRegTimeDesc(_search, "Y", "N", areaName, pageable);
		} else {
			sharingList = sharingRepo.findAllByDetailContainsAndConfirmYnAndDelYnAndAreaGugunAndCategoryCategoryNameOrderByRegTimeDesc(_search, "Y", "N", areaName, catName, pageable);
		}
		
		Page<SharingDto> sharingDtoList = sharingList.map(s -> {
			SharingDto sharingDto = SharingDto.valueOf(s);
			SharingImgDto sharingImgDto = sharingImgService.getSharingImgDto(s.getId());
			
			sharingDto.setImgUrl(sharingImgDto == null ? null : sharingImgDto.getImgUrl());
			sharingDto.setHeartCount(sharingHeartService.getSharingHeartCount(s.getId()));
			
			return sharingDto;
		});
		return sharingDtoList;
	}
	
	public void saveSharing(SharingFormDto sharingFormDto, List<MultipartFile> sharingImgFileList) throws Exception {
		Sharing sharing = sharingFormDto.createSharing();
		
		Category category = categoryRepo.findById(sharingFormDto.getCategoryId()).orElseThrow(EntityNotFoundException::new);
		Area area = areaRepo.findById(sharingFormDto.getAreaId()).orElseThrow(EntityNotFoundException::new);
		
//		로그인된 멤버 지정
		sharing.setMember(memberRepo.findById(1L).get());
		
		sharing.setCategory(category);
		sharing.setArea(area);

		sharingRepo.save(sharing);
		
		for (int i = 0; i < sharingImgFileList.size(); i++) {
			SharingImg sharingImg = new SharingImg();
			sharingImg.setSharing(sharing);
			
			sharingImg.setRepimgYn(i == 0 ? "Y" : "N");
			sharingImgService.saveImg(sharingImg, sharingImgFileList.get(i));
		}
	}
	
	public void updateSharing(SharingFormDto sharingFormDto, List<MultipartFile> sharingImgFileList) throws Exception {
		Sharing sharing = sharingRepo.findById(sharingFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		
		Category category = categoryRepo.findById(sharingFormDto.getCategoryId()).orElseThrow(EntityNotFoundException::new);
		Area area = areaRepo.findById(sharingFormDto.getAreaId()).orElseThrow(EntityNotFoundException::new);
		
		sharing.setCategory(category);
		sharing.setArea(area);
		
		sharing.setName(sharingFormDto.getName());
		sharing.setDetail(sharingFormDto.getContent());
		sharing.setUpDateTime(LocalDateTime.now());
		
		if (sharingImgFileList.size() >= 0 && !sharingImgFileList.get(0).isEmpty()) {
			sharingImgService.deleteImgsBySharingId(sharingFormDto.getId());
			for (int i = 0; i < sharingImgFileList.size(); i++) {
				SharingImg sharingImg = new SharingImg();
				sharingImg.setSharing(sharing);
				
				sharingImg.setRepimgYn(i == 0 ? "Y" : "N");
				sharingImgService.saveImg(sharingImg, sharingImgFileList.get(i));
			}
		}
	}
	
	public void deleteSharing(Long sharingId) {
		Sharing sharing = sharingRepo.findById(sharingId).orElseThrow(EntityNotFoundException::new);
		sharing.setDelYn("Y");
	}
	
	public SharingFormDto getSharingFormDto(Long sharingId) {
		Sharing sharing = sharingRepo.findById(sharingId).orElseThrow(EntityNotFoundException::new);
		return new SharingFormDto(sharing);
	}

	public Page<SharingDto> getSharingDtoList(Long memberId, Pageable pageable) {
		Page<Sharing> sharingList = sharingRepo.findByMemberIdAndDelYnOrderByRegTimeDesc(memberId, "N", pageable);
		
		Page<SharingDto> sharingDtoList = sharingList.map(s -> {
			SharingImgDto sharingImgDto = sharingImgService.getSharingImgDto(s.getId());
			SharingDto sharingDto = SharingDto.valueOf(s, sharingImgDto == null ? null : sharingImgDto.getImgUrl());
			return sharingDto;
		});
		
		return sharingDtoList;
	}

//	public Page<SharingDto> getAdoptedSharingDtoList(Long memberId, Pageable pageable) {
//		Page<Sharing> sharingList = sharingRepo.findByMemberIdAndDelYnOrderByRegTimeDesc(memberId, "N", pageable);
//		
//		Page<SharingDto> sharingDtoList = sharingList.map(s -> {
//			SharingImgDto sharingImgDto = sharingImgService.getSharingImgDto(s.getId());
//			SharingDto sharingDto = SharingDto.valueOf(s, sharingImgDto.getImgUrl());
//			return sharingDto;
//		});
//
//		Page<Story> storyList = storyRepo.findByMemberIdAndChooseYnAndDelYnOrderByRegTimeDesc(Long memberId, "Y", "N");
//		
//		Page<SharingDto> sharingDtoList = storyList.map(story -> {
//			Sharing sharing = story.getSharing();
//			SharingImgDto sharingImgDto = sharingImgService.getSharingImgDto(sharing.getId());
//			SharingDto sharingDto = sharingDto.valueOf(sharing, sharingImgDto.getImgUrl());
//			return sharingDto;
//		});
//		
//		return sharingDtoList;
//	}
	
	public Page<SharingDto> getAdminSharingDtoList(Pageable pageable, String filter, String search) {
		Page<Sharing> sharingList = null;
		
		if (filter != null && search != null) {
			if (filter.equals("title")) {
				sharingList = sharingRepo.findByNameContainsAndDelYnOrderByRegTimeDesc(search, "N", pageable);
			} else if (filter.equals("content")) {
				sharingList = sharingRepo.findByDetailContainsAndDelYnOrderByRegTimeDesc(search, "N", pageable);
			} else if (filter.equals("author")) {
//				List<Member> member = memberRepo.findAllByNickNameContains(String nickName);
//				sharingList = sharingRepo.findByMemberIdIn(member.stream().map(Memeber::getId).toList());
			}
			
		} else {
			sharingList = sharingRepo.findAllByDelYnOrderByRegTimeDesc("N", pageable);
		}

		Page<SharingDto> sharingDtoList = sharingList.map(SharingDto::valueOf);
		
		return sharingDtoList;
	}
	
	public void approveSharings(List<Long> sharingIdList) {
		List<Sharing> sharingList = sharingRepo.findAllByIdIn(sharingIdList);
		sharingList.forEach(s -> {
			s.setUpDateTime(LocalDateTime.now());
			s.setStartDate(LocalDate.now());
			s.setEndDate(LocalDate.now().plusDays(7));
			if (s.getConfirmYn().equals("N") && s.getDone().equals("N"))
				s.setConfirmYn("Y");
		});
	}

	public void approveSharing(Long sharingId, int point) {
		Sharing sharing = sharingRepo.findById(sharingId).orElseThrow(EntityNotFoundException::new);
		sharing.setPoint(point);
		sharing.setUpDateTime(LocalDateTime.now());
		sharing.setStartDate(LocalDate.now());
		sharing.setEndDate(LocalDate.now().plusDays(7));
		if (sharing.getConfirmYn().equals("N") && sharing.getDone().equals("N"))
			sharing.setConfirmYn("Y");
	}
	
	public void deleteSharings(List<Long> sharingIdList) {
		List<Sharing> sharingList = sharingRepo.findAllByIdIn(sharingIdList);
		sharingList.forEach(s -> {
			s.setUpDateTime(LocalDateTime.now());
			s.setDelYn("Y");
		});
	}
}
