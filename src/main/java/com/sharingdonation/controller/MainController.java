package com.sharingdonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

	@GetMapping("")
	public String modal() {
		return "story-modal-test";
	}
	
	@GetMapping("/list")
	public String list() {
		return "story-admin-list";
	}
	
	@GetMapping("/detail")
	public String detail() {
		return "story-admin-detail";
	}
	
	@GetMapping("/mypage/list")
	public String mylist() {
		return "mypage-story-list";
	}
	
	@GetMapping("/mypage/detail")
	public String mydetail() {
		return "mypage-story-detail";
	}
	
	@GetMapping("/mypage/")
	public String mymain() {
		return "mypage-main";
	}
	
	@GetMapping("/mypage/privacy")
	public String myprivacy() {
		return "mypage-privacy";
	}
	@GetMapping("/mypage/enterpriceprivacy")
	public String myEnterpricePrivacy() {
		return "mypage-Enterprice-privacy";
	}
	

}
