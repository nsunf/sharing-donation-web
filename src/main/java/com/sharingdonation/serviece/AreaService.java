package com.sharingdonation.serviece;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sharingdonation.dto.AreaDto;
import com.sharingdonation.entity.Area;
import com.sharingdonation.repository.AreaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AreaService {
	private final AreaRepository areaRepo;

	public void addTestData() {

		String[] guList = { "jongno-gu", "jung-gu", "yongsan-gu", "seongdong-gu", "gwangjin-gu", "dongdaemun-gu",
				"jungnang-gu", "seongbuk-gu", "gangbuk-gu", "dobong-gu", "nowon-gu", "eunpyeong-gu", "seodaemun-gu", "mapo-gu",
				"yangcheon-gu", "gangseo-gu", "guro-gu", "geumcheon-gu", "yeongdeungpo-gu", "dongjak-gu", "gwanak-gu",
				"seocho-gu", "gangname-gu", "songpa-gu", "gangdong-gu" };
		String[] guListKor = { "종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구",
				"마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구" };

		List<Area> list = new ArrayList<>();
		
		for (int i = 0; i < guList.length; i++) {
			Area area = new Area();
			area.setSido("서울");
			area.setGugun(guListKor[i]);
			list.add(area);
		}
		
		areaRepo.saveAll(list);
		
	}

	public List<AreaDto> getAreaList() {
		return areaRepo.findAll().stream().map(AreaDto::of).toList();
	}
}
