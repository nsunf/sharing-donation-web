package com.sharingdonation.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.constant.MoveType;
import com.sharingdonation.dto.DonationAdminFormDto;
import com.sharingdonation.dto.DonationDto;
import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.dto.DonationImgDto;
import com.sharingdonation.dto.SearchDto;
import com.sharingdonation.dto.ListDonationDto;
import com.sharingdonation.dto.PointDto;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationImg;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Point;
import com.sharingdonation.repository.DonationImgRepository;
import com.sharingdonation.repository.DonationRepository;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.PointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {
	private final DonationRepository donationRepository;
	private final DonationImgService donationImgService;
	private final DonationImgRepository donationImgRepository;
	private final PointRepository pointRepository;
	private final MemberRepository memberRepository;
	
	public Long saveDonation(DonationFormDto donationFormDto, List<MultipartFile> donationImgFileList) throws Exception {
		Donation donation = donationFormDto.createDonation();
		donationRepository.save(donation);
		
		for(int i = 0; i < donationImgFileList.size(); i++) {
			DonationImg donationImg = new DonationImg();
			donationImg.setDonation(donation);
			
			donationImg.setRepimgYn(i == 0 ? "Y" : "N");

			donationImgService.saveDonationImg(donationImg, donationImgFileList.get(i));
		}
		return donation.getId();
	}
	
	@Transactional(readOnly = true)
	public DonationFormDto getDonationDtl(Long donationId) {
		List<DonationImg> donationImgList = donationImgRepository.findByDonationIdOrderByIdAsc(donationId);
		List<DonationImgDto> donationImgDtoList = new ArrayList<>();
		
		for(DonationImg donationImg : donationImgList) {
			DonationImgDto donationImgDto = DonationImgDto.of(donationImg);
			System.out.println("DonationFormDto getDonationDtls :::" +  donationImgDto.getImgUrl());
			donationImgDtoList.add(donationImgDto);
		}
		
		Donation donation = donationRepository.findById(donationId)
				.orElseThrow(EntityNotFoundException::new);

		
		DonationFormDto donationFormDto = DonationFormDto.of(donation);
		
		int pointSum = (int)pointRepository.selectTotalPoint(donationId);
		System.out.println(pointSum);
		double pointPer = 0;
		
		if (pointSum > 0) {
			pointPer = (double)((double)pointSum / (double)donation.getGoalPoint()) * 100; //double 로 계산해야 정상적으로 계산이 된다.
		}
		
		donationFormDto.setPointPer((int) pointPer);
		
		donationFormDto.setDonationImgDtoList(donationImgDtoList);
		
		return donationFormDto;
	}
	
	
	
	public Long updateDonation(DonationFormDto donationFormDto, List<MultipartFile> donationImgFileList) throws Exception {
		Donation donation = donationRepository.findById(donationFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);
				
		donation.updateDonation(donationFormDto);
		
//		List<Long> donationImgIds = donationFormDto.getDonateionImgIds();
		
//		for(int i = 0; i<donationImgFileList.size(); i++) {
//			donationImgService.updateDonationImg(donationImgIds.get(i), donationImgFileList.get(i));
//		}
		
		if (donationImgFileList.size() >= 0 && !donationImgFileList.get(0).isEmpty()) {
			donationImgService.deleteImgsByDonationId(donationFormDto.getId());
			for (int i = 0; i < donationImgFileList.size(); i++) {
				DonationImg donationImg = new DonationImg();
				donationImg.setDonation(donation);
				
				donationImg.setRepimgYn(i == 0 ? "Y" : "N");
				donationImgService.saveDonationImg(donationImg, donationImgFileList.get(i));
			}
		}
		
		return donation.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<ListDonationDto> getListDonationPage(SearchDto searchDto, Pageable pageable) {
		Page<ListDonationDto> donationList = donationRepository.getListDonationPage(searchDto, pageable);
		for(ListDonationDto donation : donationList) {
			double pointPer = 0 ;
			
			if(donation.getPointSum() > 0 ) {
				pointPer = (double)((double)donation.getPointSum() / (double)donation.getGoalPoint()) * 100; //double 로 계산해야 정상적으로 계산이 된다.
			}
			donation.setPointPer((int)pointPer);
			
//			System.out.println(donation.getId() + ":"+ donation.getPointSum() +":"+ donation.getGoalPoint() +":"+ donation.getPointPer());
		}
		return donationList;
	}
	
	
	
//	public Long saveDonation(DonationFormDto donationFormDto, List<MultipartFile> donationImgFileList) throws Exception {
//		Donation donation = donationFormDto.createDonation();
//		donationRepository.save(donation);
//		
//		for(int i = 0; i < donationImgFileList.size(); i++) {
//			DonationImg donationImg = new DonationImg();
//			donationImg.setDonation(donation);
//			
//			donationImg.setRepimgYn(i == 0 ? "Y" : "N");
//
//			donationImgService.saveDonationImg(donationImg, donationImgFileList.get(i));
//		}
//		return donation.getId();
//	}
	
	public Long pointDonation(PointDto pointDto, Principal principal) {
		
		
//		Point point = pointRepository.findById(donationId)
//                .orElseThrow(EntityNotFoundException::new);
//		
//		Principal principa = null;
		
		String email = principal.getName();
		
		System.out.println(principal.getName());
		Member member = memberRepository.findByEmail(email);
		
//		sharingService.approveSharing(member.getId(), donationId, point);
		Long points = pointRepository.pointSearch(member.getId());
		
		if (points <= 0) {
			System.out.println("service pointDonation pointspoints : " + points);
			throw new IllegalStateException("사용 포인트가 없습니다.");
		}
		
//		List<OrderItem> orderItemList = new ArrayList<>(); 
		System.out.println("service pointdonation pointDto.getDonationId() : " + pointDto.getDonationId());
		pointDto.setMemberId(member.getId());
		pointDto.setRegTime(LocalDateTime.now());
		pointDto.setMoveType(MoveType.MINUS);
		System.out.println("pointDto.getMemberId() :: " + pointDto.getMemberId());
		Point point = pointDto.createPoint();
		pointRepository.save(point);
		
//		Point point = Point.createOrder(member, orderItemList);
		
//		pointRepository.save(point);
		
		return point.getId();
	}
	
	
	public Long updateAdminDonation(DonationAdminFormDto donationAdminFormDto, List<MultipartFile> donationImgFileList) throws Exception {
		Donation donation = donationRepository.findById(donationAdminFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);
		
//				System.out.println("service updateAdminDonation");
		donation.updateAdminDonation(donationAdminFormDto);
		
//		List<Long> donationImgIds = donationAdminFormDto.getDonateionImgIds();
		
//		for(int i = 0; i<donationImgFileList.size(); i++) {
//			donationImgService.updateDonationImg(donationImgIds.get(i), donationImgFileList.get(i));
//		}
		System.out.println(donationImgFileList.size() + ":" + donationImgFileList.get(0).isEmpty());
		if (donationImgFileList.size() >= 0 && !donationImgFileList.get(0).isEmpty()) {
//			System.out.println(" !donationImgFileList.get(0).isEmpty() -- " + donationAdminFormDto.getId());
			donationImgService.deleteImgsByDonationId(donationAdminFormDto.getId());
			
			for (int i = 0; i < donationImgFileList.size(); i++) {
				DonationImg donationImg = new DonationImg();
				donationImg.setDonation(donation);
				
				donationImg.setRepimgYn(i == 0 ? "Y" : "N");
				donationImgService.saveDonationImg(donationImg, donationImgFileList.get(i));
			}
		}
		
		return donation.getId();
	}
	
	
	
	
	@Transactional(readOnly = true)
	public DonationAdminFormDto getAdminDonationDtl(Long donationId) {
		List<DonationImg> donationImgList = donationImgRepository.findByDonationIdOrderByIdAsc(donationId);
		List<DonationImgDto> donationImgDtoList = new ArrayList<>();
		
		for(DonationImg donationImg : donationImgList) {
			DonationImgDto donationImgDto = DonationImgDto.of(donationImg);
			donationImgDtoList.add(donationImgDto);
		}
		
		Donation donation = donationRepository.findById(donationId)
				.orElseThrow(EntityNotFoundException::new);
		
		DonationAdminFormDto donationAdminFormDto = DonationAdminFormDto.of(donation);
		String status = donationAdminFormDto.getDone().equals("Y") ? "완료" : (donationAdminFormDto.getConfirmYn().equals("Y") ? "진행중": "승인대기");
		donationAdminFormDto.setStatus(status);
		donationAdminFormDto.setDonationImgDtoList(donationImgDtoList);
		
		return donationAdminFormDto;
	}
	
	
	public Page<DonationDto> getDonationDtoListByMemberId(Long memberId, Pageable pageable) {
		Page<Donation> donationList = donationRepository.findByMemberIdAndDelYnOrderByRegTimeDesc(memberId, "N", pageable);
		
		Page<DonationDto> donationDtoList = donationList.map(s -> {
			DonationImgDto donationImgDto = donationImgService.getDonationImgDto(s.getId());
			DonationDto donationDto = DonationDto.valueOf(s, donationImgDto == null ? null : donationImgDto.getImgUrl());
			return donationDto;
		});
		
		return donationDtoList;
	}
	
	@Transactional(readOnly = true)
	public Page<DonationDto> getAdminListDonationPage(SearchDto donationSearchDto, Pageable pageable) {
		Page<DonationDto> donationList = donationRepository.getAdminListDonationPage(donationSearchDto, pageable);
		for(DonationDto donation : donationList) {
			double pointPer = 0 ;
			
			if(donation.getPointSum() > 0 ) {
				pointPer = (double)((double)donation.getPointSum() / (double)donation.getGoalPoint()) * 100; //double 로 계산해야 정상적으로 계산이 된다.
			}
			donation.setPointPer((int)pointPer);
			
			String status = donation.getDone().equals("Y") ? "완료" : (donation.getConfirmYn().equals("Y") ? "진행중": "승인대기");
			donation.setStatus(status);
//			
//			System.out.println(donation.getId() + ":"+ donation.getPointSum() +":"+ donation.getGoalPoint() +":"+ donation.getPointPer());
		}
		return donationList;
	}
}
