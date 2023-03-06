package com.sharingdonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingdonation.service.SharingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
	private final SharingService sharingService;

	@GetMapping(value={"/", "/sharing"})
	public String main(Model model) {
		model.addAttribute("sharedCount", sharingService.getNumOfShared());
		model.addAttribute("currentSharingCount", sharingService.getCurrentSharingCount());
		return "main";
	}
	
	@GetMapping("/intro")
	public String intro() {
		return "intro";
	}
}
