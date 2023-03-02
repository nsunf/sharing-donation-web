package com.sharingdonation.controller;
 

import java.io.Console;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharingdonation.dto.MyPageEnterPricePrivacyDto;
import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.MyPagePrivacyDto;
import com.sharingdonation.dto.MyPageStoryDetailDto;
import com.sharingdonation.dto.MyPageStoryListDto;
import com.sharingdonation.entity.Member;
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
		MyPageMainDto myPageMainDto = myPageService.getMyPageMain(memberId);
		
		
		
		//실험용 코드
		//MyPageMainDto myPageMainDto = new MyPageMainDto(memberId, 0, memberId, memberId, memberId, memberId, "김김김", null);
		model.addAttribute("mypage",myPageMainDto);
		return "/myPage/mypageMain";
	}
	
	@GetMapping("/mypage/privacy/{memberId}")
	public String myprivacy(@PathVariable("memberId") Long memberId, Model model) {
		//진짜코드
		MyPagePrivacyDto myPagePrivacyDto = myPageService.getMyPagePrivacy(memberId);
		
		//가짜코드
		//MyPagePrivacyDto myPagePrivacyDto =  new MyPagePrivacyDto(memberId, "김김김", "adf@adf.com",null, "닉눼임", "12-12", "서울시", "서울시랜다", null);
		
		model.addAttribute("mypage",myPagePrivacyDto);
		return "/myPage/mypage-privacy";
	}
	
	
	
	@PostMapping("/mypage/privacy/{memberId}")
	public @ResponseBody ResponseEntity myprivacyUpdate(@RequestBody  @Valid MyPagePrivacyDto myPagePrivacyDto,BindingResult bindingResult, @PathVariable("memberId") Long memberId ) {

		  if(bindingResult.hasErrors()){
	            StringBuilder sb = new StringBuilder();
	            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

	            for (FieldError fieldError : fieldErrors) {
	                sb.append(fieldError.getDefaultMessage());
	            }
	            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
	        }
		  try {
			  
			  Long result = myPageService.myPrivacyUpdate(myPagePrivacyDto,memberId);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		 return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/mypage/enterpriceprivacy/{memberId}")
	public String myEnterpricePrivacy(@PathVariable("memberId") Long memberId, Model model){
		MyPageEnterPricePrivacyDto myPageEnterPricePrivacyDto = myPageService.getMyPageEnterPricePrivacyDto(memberId);
		model.addAttribute("mypage",myPageEnterPricePrivacyDto);
		return "/myPage/mypage-Enterprice-privacy";
	}
	
	@PostMapping("/mypage/enterpriceprivacy/{memberId}")
	public @ResponseBody ResponseEntity myEnterpricePrivacyUpdate(@RequestBody  @Valid MyPageEnterPricePrivacyDto myPageEnterPricePrivacyDto,BindingResult bindingResult, @PathVariable("memberId") Long memberId ) {

		  if(bindingResult.hasErrors()){
	            StringBuilder sb = new StringBuilder();
	            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

	            for (FieldError fieldError : fieldErrors) {
	                sb.append(fieldError.getDefaultMessage());
	            }
	            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
	        }
		  try {
			 
			  Long result = myPageService.myEnterpricePrivacyUpdate(myPageEnterPricePrivacyDto,memberId);
			  
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		 return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}
	
	
	
	
	
	@GetMapping({"/mypage/story/{memberId}", "/mypage/story/{memberId}/{page}"})
	public String myPageStory(@PathVariable("memberId") Long memberId, @PathVariable("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		
		Page<MyPageStoryListDto> myPageStoryListDto = myPageService.getMyPageStoryList(memberId, pageable);
		MyPageMainDto myPageMainDto = myPageService.getMyPageMain(memberId);
		
		System.out.println(myPageStoryListDto);
		model.addAttribute("page", pageable.getPageNumber()); //현재 페이지
		model.addAttribute("mypage",myPageMainDto);
		model.addAttribute("myPageStoryListDto",myPageStoryListDto);
		model.addAttribute("maxPage",5);
		
		return "/mypage/mypage-story-list";
	}
	

	
	@GetMapping("/mypage/story/detail/{memberId}/{storyId}")
	public String myPageStoryDetail(@PathVariable("memberId") Long memberId, @PathVariable("storyId") Long storyId, Model model) {
		MyPageStoryDetailDto myPageStoryDetailDto = myPageService.getMyPageStoryDetail(memberId, storyId);
		MyPageMainDto myPageMainDto = myPageService.getMyPageMain(memberId);
		model.addAttribute("detail",myPageStoryDetailDto);
		model.addAttribute("mypage",myPageMainDto);
		
		return "/mypage/mypage-story-detail";
	}
	
	@PostMapping("/mypage/story/detail/{memberId}/{storyId}")
	public @ResponseBody ResponseEntity myPageStoryDetailUpdate(@RequestBody  @Valid MyPageStoryDetailDto myPageStoryDetailDto ,BindingResult bindingResult, @PathVariable("memberId") Long memberId,@PathVariable("storyId") Long storyId ) {

		 
		  if(bindingResult.hasErrors()){
	            StringBuilder sb = new StringBuilder();
	            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

	            for (FieldError fieldError : fieldErrors) {
	                sb.append(fieldError.getDefaultMessage());
	            }
	            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
	        }
		  try {
			  
			  Long result = myPageService.myPageStoryDetailUpdate(myPageStoryDetailDto,memberId,storyId);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		 return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}
	
	
}
