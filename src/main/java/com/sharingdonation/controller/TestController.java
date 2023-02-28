package com.sharingdonation.controller;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
	
	

	@GetMapping("")
	public String test() {
		return "admin/sharing_md_ctr";
	}
	
	@GetMapping("/dummydata")
	public String generateTestData() {
//	카테고리
//		지역
//		멤버
//		나눔
//		나눔 이미지
//		나눔 완료
//		나눔 완료 좋아요
//		기부
//		기부 이미지
//		기부 완료
//		기부 완료 좋아요
//		사연

		return null;

	
		
	}
}
