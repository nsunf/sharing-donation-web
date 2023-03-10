package com.sharingdonation.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sharingdonation.dto.SearchDto;
import com.sharingdonation.dto.ListDonationDto;
import com.sharingdonation.service.DonationService;
import com.sharingdonation.service.PointService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PointContorller {
	private final PointService pointService;
	private final DonationService donationService;
	
	@GetMapping(value = "/point")
	public String adminPointList(SearchDto searchDto, Optional<Integer> page, Model model) {
		
//		Pageable pageable = PageRequest.of(page.isPresent() ? page.get()-1 : 0, 6);
		Pageable pageable = PageRequest.of(page.orElse(0), 10);
//		Page<ListDonationDto> donationList = pointService.getListDonationPage(searchDto, pageable);
		
//		System.out.println(donationList.getContent());
//		for(ListDonationDto ln : donationList.getContent()) {
//			System.out.println("ln.getGoalPoint():" + ln.getGoalPoint() + ", ln.getPointSum() : " + ln.getPointSum() + ", ln.getHeartCount() : " +ln.getHeartCount());
//		}
//		model.addAttribute("donationList", donationList);
		model.addAttribute("searchDto", searchDto);
		model.addAttribute("maxPage", 5);
//		System.out.println("donation_list_check");
		return "admin/pointList";
	}
}