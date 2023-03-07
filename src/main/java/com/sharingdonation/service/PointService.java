package com.sharingdonation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.dto.PointDto;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.DonationImg;
import com.sharingdonation.entity.Point;
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
}
