package com.sharingdonation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingdonation.entity.Category;
import com.sharingdonation.service.TestDataService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
	
	private final TestDataService testDataService;

	@GetMapping("")
	public String test() {
		return "main";
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
