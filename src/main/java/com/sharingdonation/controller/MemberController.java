package com.sharingdonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sharingdonation.dto.DonationFormDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	@GetMapping(value = "/member")
	public String member(Model model) {
		model.addAttribute(model);
		return "member/memberForm";
	}
	
	@PostMapping(value = "/member")
	public String memer() {
		return "redirect:/";
	}
	
	@GetMapping(value = "/member/{memberId}")
	public String donationDtl(Model model, @PathVariable("donationId") Long DonationId) {
		
//		model.addAttribute("member", memberFormDto);
		return "member/memberDtl";
	}
}
