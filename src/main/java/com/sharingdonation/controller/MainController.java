package com.sharingdonation.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.service.AreaService;
import com.sharingdonation.service.CategoryService;
import com.sharingdonation.service.DonationService;
import com.sharingdonation.service.SharingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
	private final SharingService sharingService;
	private final AreaService areaService;
	private final DonationService donationService;

	@GetMapping(value={"/", "/sharing"})
	public String main(Model model) {
		Long currentSharingCount = sharingService.getCurrentSharingCount();
//		if (currentSharingCount == null) currentSharingCount = 0L;

		model.addAttribute("sharedCount", sharingService.getNumOfShared());
		model.addAttribute("donatedCount", donationService.getNumOfDonated());
		model.addAttribute("currentSharingCount", currentSharingCount);
		model.addAttribute("areaDtoList", areaService.getAreaList());
		model.addAttribute("mainDonation", donationService.getDonationMain());
		return "main";
	}
	
	@GetMapping(value="/intro")
	public String intro() {
		return "intro";
	}
}
