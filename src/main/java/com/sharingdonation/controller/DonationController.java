package com.sharingdonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DonationController {
	@GetMapping(value ="/donation")
	public String donation(Model model) {
		model.addAttribute(model);
		return "donation/donationForm";
	}
	
	@PostMapping(value = "/donation")
	public String donation() {
		return "redirect:/";
	}
}
