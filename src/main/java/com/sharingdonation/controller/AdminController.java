package com.sharingdonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sharingdonation.dto.MemberAllDto;
import com.sharingdonation.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminService;

	@GetMapping("/admin/story")
	public String adminStory() {
		return "/admin/storyList";
	}
	
	@GetMapping("/admin/story/detail")
	public String adminStoryDetail() {
		return "/admin/storyDetail";
	}
	
	@GetMapping("/admin/management/{memberId}")
	public String adminManagement(@PathVariable("memberId") Long memberId, Model model) {
		MemberAllDto memberAllDto = adminService.findByMemberId(memberId);
		model.addAttribute("member",memberAllDto);
		
		return "/admin/MemberManagement";
	}
	

	
}
