package com.sharingdonation.controller;
 

import java.io.Console;
import java.security.Principal;
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
	@GetMapping("/mypage")
	public String myPageMain(Principal principal, Model model) {
		MyPageMainDto myPageMainDto = myPageService.getMyPageMain(principal);
		model.addAttribute("mypage",myPageMainDto);
		return "/myPage/mypageMain";
	}
	
	@GetMapping("/mypage/privacy")
	public String myprivacy(Principal principal,Model model) {
		MyPagePrivacyDto myPagePrivacyDto = myPageService.getMyPagePrivacy(principal);
		model.addAttribute("mypage",myPagePrivacyDto);
		return "/myPage/mypage-privacy";
	}
	
	
	
	@PostMapping("/mypage/privacy")
	public @ResponseBody ResponseEntity myprivacyUpdate(@RequestBody  @Valid MyPagePrivacyDto myPagePrivacyDto,BindingResult bindingResult,Principal principal) {

		  if(bindingResult.hasErrors()){
	            StringBuilder sb = new StringBuilder();
	            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
	            for (FieldError fieldError : fieldErrors) {
	                sb.append(fieldError.getDefaultMessage());
	            }
	            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
	        }
		  try {
			  Long result = myPageService.myPrivacyUpdate(myPagePrivacyDto,principal);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		 return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	
	
	
	@GetMapping("/mypage/enterpriceprivacy")
	public String myEnterpricePrivacy(Principal principal, Model model){
		MyPageEnterPricePrivacyDto myPageEnterPricePrivacyDto = myPageService.getMyPageEnterPricePrivacyDto(principal);
		model.addAttribute("mypage",myPageEnterPricePrivacyDto);
		return "/myPage/mypage-Enterprice-privacy";
	}
	
	@PostMapping("/mypage/enterpriceprivacy")
	public @ResponseBody ResponseEntity myEnterpricePrivacyUpdate(@RequestBody  @Valid MyPageEnterPricePrivacyDto myPageEnterPricePrivacyDto,BindingResult bindingResult, Principal principal ) {

		  if(bindingResult.hasErrors()){
	            StringBuilder sb = new StringBuilder();
	            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

	            for (FieldError fieldError : fieldErrors) {
	                sb.append(fieldError.getDefaultMessage());
	            }
	            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
	        }
		  try {
			 
			  Long result = myPageService.myEnterpricePrivacyUpdate(myPageEnterPricePrivacyDto,principal);
			  
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		 return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	
	
	
	
	@GetMapping({"/mypage/story", "/mypage/story/{page}"})
	public String myPageStory(Principal principal, @PathVariable("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		
		Page<MyPageStoryListDto> myPageStoryListDto = myPageService.getMyPageStoryList(principal, pageable);
		MyPageMainDto myPageMainDto = myPageService.getMyPageMain(principal);
		
		System.out.println(myPageStoryListDto);
		model.addAttribute("page", pageable.getPageNumber()); //현재 페이지
		model.addAttribute("mypage",myPageMainDto);
		model.addAttribute("myPageStoryListDto",myPageStoryListDto);
		model.addAttribute("maxPage",5);
		
		return "/mypage/mypage-story-list";
	}
	

	
	@GetMapping("/mypage/story/detail/{storyId}")
	public String myPageStoryDetail( @PathVariable("storyId") Long storyId, Principal principal, Model model) {
		MyPageStoryDetailDto myPageStoryDetailDto = myPageService.getMyPageStoryDetail(principal, storyId);
		MyPageMainDto myPageMainDto = myPageService.getMyPageMain(principal);
		model.addAttribute("detail",myPageStoryDetailDto);
		model.addAttribute("mypage",myPageMainDto);
		
		return "/mypage/mypage-story-detail";
	}
	
	@PostMapping("/mypage/story/detail/update/{storyId}")
	public @ResponseBody ResponseEntity myPageStoryDetailUpdate(@RequestBody  @Valid MyPageStoryDetailDto myPageStoryDetailDto ,BindingResult bindingResult, @PathVariable("storyId") Long storyId, Principal principal ) {

		 
		  if(bindingResult.hasErrors()){
	            StringBuilder sb = new StringBuilder();
	            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

	            for (FieldError fieldError : fieldErrors) {
	                sb.append(fieldError.getDefaultMessage());
	            }
	            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
	        }
		  
		  try {
			  
			  Long result = myPageService.myPageStoryDetailUpdate(myPageStoryDetailDto, principal,storyId);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		 return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	@PostMapping("/mypage/story/detail/delete/{storyId}")
	public @ResponseBody ResponseEntity myPageStoryDetailDelete(@RequestBody  @Valid MyPageStoryDetailDto myPageStoryDetailDto ,BindingResult bindingResult, @PathVariable("storyId") Long storyId, Principal principal ) {

		 
		  if(bindingResult.hasErrors()){
	            StringBuilder sb = new StringBuilder();
	            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

	            for (FieldError fieldError : fieldErrors) {
	                sb.append(fieldError.getDefaultMessage());
	            }
	            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
	        }
		 
		  try {
			  
			   Long result = myPageService.mypageStoryDetailDelete(principal,storyId);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		 return new ResponseEntity<String>("Delete Success", HttpStatus.OK);
	}
	
	
}
