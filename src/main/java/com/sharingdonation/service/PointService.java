package com.sharingdonation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.constant.MoveType;
import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.dto.PointDto;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationImg;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Point;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.Story;
import com.sharingdonation.repository.DonationImgRepository;
import com.sharingdonation.repository.DonationRepository;
import com.sharingdonation.repository.PointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PointService {
	private final PointRepository pointRepository;
	
	public Long savePoint(PointDto pointDto) throws Exception {
		Point point;
		point = pointDto.createPoint();

		pointRepository.save(point);
		return point.getId();
	}
	
	// 나눔 사연 채택시 포인트 지급
	public void saveSharingPoint(Story story) {
		Sharing sharing = story.getSharing();
		Member sharingMember = sharing.getMember();
		//Member sharedMember = story.getMember();
		
		Point point = new Point();
		point.setMember(sharingMember);
		point.setSharing(sharing);
		point.setMoveType(MoveType.PLUS);
		point.setPoint(sharing.getPoint());
		point.setRegTime(LocalDateTime.now());

		point.setComment("나눔 사연 채택");
		
		int calcedMemberPoint = sharingMember.getPoint() + sharing.getPoint();
		sharingMember.setPoint(calcedMemberPoint);
		
		pointRepository.save(point);
	}
	//
}
