package com.sharingdonation.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.MyPagePrivacyDto;
import com.sharingdonation.service.MyPageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyPageController {
	
	private final MyPageService myPageService;
	
	/*마이페이지 메인 화면, 로그인된 memberId를 넘겨 받음*/
	@GetMapping("/mypage/{memberId}")
	public String myPageMain(@PathVariable("memberId") Long memberId, Model model) {
		
		//진짜 코드
		//MyPageMainDto myPageMainDto = myPageService.getMyPageMain(memberId);
		
		//실험용 코드
		MyPageMainDto myPageMainDto = new MyPageMainDto(memberId, 0, memberId, memberId, memberId, memberId, "김김김", null);
		model.addAttribute("mypage",myPageMainDto);
		return "/myPage/mypageMain";
	}
	
	@GetMapping("/mypage/privacy/{memberId}")
	public String myprivacy(@PathVariable("memberId") Long memberId, Model model) {
		//진짜코드
		//MyPagePrivacyDto myPagePrivacyDto = myPageService.getMyPagePrivacy(memberId);
		Date date = new Date();
		
		MyPagePrivacyDto myPagePrivacyDto =  new MyPagePrivacyDto(memberId, "김김김", "adf@adf.com",null, "닉눼임", "12-12", "서울시", "서울시랜다", null);
		
		model.addAttribute("mypage",myPagePrivacyDto);
		return "/myPage/mypage-privacy";
	}
	
	@GetMapping("/mypage/enterpriceprivacy")
	public String myEnterpricePrivacy() {
		return "/myPage/mypage-Enterprice-privacy";
	}
	
}
