package com.sharingdonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.service.DonationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DonationController {
	
	private final DonationService donationService;
	
	@GetMapping(value = "/donation")
	public String donation(Model model) {
//		model.addAttribute(model);
		return "donation/donationForm";
	}
	
	@PostMapping(value = "/donation")
	public String donation() {
		return "redirect:/";
	}
	
	@GetMapping(value = "/donation/{donationId}")
	public String donationDtl(Model model, @PathVariable("donationId") Long DonationId) {
//		DonationFormDto donationFormDto = donationService.getDonationDtl(DonationId);
//		model.addAttribute("donation", donationFormDto);
		return "donation/donationDtl";
	}
}
