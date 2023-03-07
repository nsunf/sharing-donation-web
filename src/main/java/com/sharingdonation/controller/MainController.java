package com.sharingdonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingdonation.service.AreaService;
import com.sharingdonation.service.CategoryService;
import com.sharingdonation.service.SharingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
	private final SharingService sharingService;
	private final AreaService areaService;

	@GetMapping(value={"/", "/sharing"})
	public String main(Model model) {
		Long currentSharingCount = sharingService.getCurrentSharingCount();
//		if (currentSharingCount == null) currentSharingCount = 0L;

		model.addAttribute("sharedCount", sharingService.getNumOfShared());
		model.addAttribute("currentSharingCount", currentSharingCount);
		model.addAttribute("areaDtoList", areaService.getAreaList());
		return "main";
	}
	

	@GetMapping(value="/intro")
	public String intro() {
		return "intro";
	}
}
