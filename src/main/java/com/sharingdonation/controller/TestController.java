package com.sharingdonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingdonation.service.TestDataService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
	
	private final TestDataService testDataService;

	@GetMapping("")
	public String test() {
		return "redirect:/intro";
	}

	@GetMapping("/init")
	public String init() {
		testDataService.initData();
		return "redirect:/intro";
	}
	
	@GetMapping("/dummyData")
	public String confirmDummyData() {

		return "dummyData";
	}
	
	@PostMapping("/dummyData")
	public String generateDummyData() {
		testDataService.generateTestData();
		return "redirect:/";
	}
}
