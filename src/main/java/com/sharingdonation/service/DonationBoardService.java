package com.sharingdonation.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.DonationBoardDto;
import com.sharingdonation.dto.DonationBoardFormDto;
import com.sharingdonation.dto.DonationBoardImgDto;
import com.sharingdonation.dto.DonationBoardSelectDto;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationBoard;
import com.sharingdonation.entity.DonationBoardImg;
import com.sharingdonation.repository.DonationBoardImgRepository;
import com.sharingdonation.repository.DonationBoardRepository;
import com.sharingdonation.repository.DonationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationBoardService {
	private final DonationBoardRepository donationBoardRepository;
	private final DonationBoardImgService donationBoardImgService;
	private final DonationBoardImgRepository donationBoardImgRepository;
	private final DonationRepository donationRepository;
	
	
	//donation data
	public List<DonationBoardSelectDto> getDonationBorardSelect(){
		List<Donation> donations = donationRepository.findAll();
		
		List<DonationBoardSelectDto> donationBoardSelectDtos = new ArrayList<>();
		
		for(Donation donation : donations) {
			DonationBoardSelectDto donationBoardSelectDto = DonationBoardSelectDto.of(donation);
			donationBoardSelectDtos.add(donationBoardSelectDto);
		}
		return donationBoardSelectDtos;
		
	}
	
	
	
	//create donation board
	public Long SaveDonationBoard(DonationBoardFormDto donationBoardFormDto, List<MultipartFile> donationBoardImgFileList) throws Exception{
		DonationBoard donationBoard = donationBoardFormDto.createDonationBoard();
		
		Donation donation = donationRepository.findById(donationBoardFormDto.getDonationId()).orElseThrow(EntityNotFoundException::new);
		donationBoard.setDonation(donation);

		donationBoardRepository.save(donationBoard);
		
		//이미지 등록
		for(int i=0; i<donationBoardImgFileList.size(); i++) {
			DonationBoardImg donationBoardImg = new DonationBoardImg();
			donationBoardImg.setDonationBoard(donationBoard);
			
			if(i == 0) {
				donationBoardImg.setRepimgYn("Y");
			}else {
				donationBoardImg.setRepimgYn("N");
			}
			
			donationBoardImgService.saveDonationBoardImg(donationBoardImg, donationBoardImgFileList.get(i));
		}
		
		return donationBoard.getId();
	}
	
	//donation board list
	@Transactional(readOnly = true)
	public Page<DonationBoard> getAdminPostDtoPage(Pageable pageable){
		return donationBoardRepository.getAdminDonationBoardPage(pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<DonationBoardDto> getDonationBoardDtoPage(Pageable pageable){
		return donationBoardRepository.getDonationBoardPage(pageable);
	}


 
	//get donation board detail
	@Transactional(readOnly = true)
	public DonationBoardFormDto getdonationBoardDetail(Long donationBoardId) {
		List<DonationBoardImg> donationBoardImgList = donationBoardImgRepository.findByDonationBoardIdOrderByIdAsc(donationBoardId);
		List<DonationBoardImgDto> donationBoardImgDtoList = new ArrayList<>();
		
		for(DonationBoardImg donationBoardImg : donationBoardImgList) {
			DonationBoardImgDto donationBoardImgDto = DonationBoardImgDto.of(donationBoardImg);
			donationBoardImgDtoList.add(donationBoardImgDto);
		}
		
		//2. donation board테이블에 있는 데이터를 가져온다.
				DonationBoard donationBoard = donationBoardRepository.findById(donationBoardId)
											 .orElseThrow(EntityNotFoundException::new);
				
				//엔티티 객체 -> dto객체로 변환
				DonationBoardFormDto donationBoardFormDto = DonationBoardFormDto.of(donationBoard);
				
				//이미지 정보를 넣어준다.
				donationBoardFormDto.setDonationBoardImgDtoList(donationBoardImgDtoList);
				
				return donationBoardFormDto;
		
	}
	
	//donation board detail
}
