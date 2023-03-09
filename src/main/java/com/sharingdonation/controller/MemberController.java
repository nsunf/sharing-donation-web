package com.sharingdonation.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.web.bind.annotation.ResponseStatus;

//import com.myshop.controller.SessionManager;
import com.sharingdonation.dto.MemberFormDto;
import com.sharingdonation.dto.CorpFormDto;
import com.sharingdonation.dto.MemberChangePwsDto;

import lombok.RequiredArgsConstructor;

import com.sharingdonation.entity.Member;
import com.sharingdonation.service.MemberService;



@RequestMapping("/auth")
@Controller
@RequiredArgsConstructor

public class MemberController {
	private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
	
    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "auth/signupNormal";
    }
	
	@PostMapping(value = "/new")
	public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()){
            return "auth/signupNormal";
        }
		
		try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
        	e.printStackTrace();
            model.addAttribute("errorMessage",  e.getMessage());
            return "auth/signupNormal";
        }

        return "redirect:/";
	} 
	
	//////////////////////////////////////////////////////////////////////////////////////
	
    @GetMapping(value = "/newCorp")
    public String CorpForm(Model model){
        model.addAttribute("corpFormDto", new CorpFormDto());
        return "auth/signupCorp";
    }
	
	@PostMapping(value = "/newCorp")
	public String newCorp(@Valid CorpFormDto corpFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()){
            return "auth/signupCorp";
        }
		
		try {
            Member corp = Member.createCorp(corpFormDto, passwordEncoder);
            memberService.saveCorp(corp);
        } catch (IllegalStateException e){
        	e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/signupCorp";
        }

        return "redirect:/";
	} 
	
    @GetMapping(value = "/login")
    public String loginMember(HttpServletResponse response, HttpSession session){
    	return "auth/login";
    }
    
    @PostMapping("/login/equals")
    public @ResponseBody ResponseEntity memberEquals(@RequestBody @Valid MemberChangePwsDto memberFormDto, BindingResult bindingResult) {
    	int result = 0;
    	if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
	  try {
		  result = memberService.memberEquals(memberFormDto.getEmail(), memberFormDto.getName(), memberFormDto.getCellphone());
	} catch (Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	  if(result == 0) {
		  return new ResponseEntity<String>("인증 실패", HttpStatus.BAD_REQUEST);
	  }
	  return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }
    
    
    @PostMapping("/login/change")
    public @ResponseBody ResponseEntity memberChange(@RequestBody @Valid MemberChangePwsDto memberFormDto, BindingResult bindingResult) {
    	int result = 0;
    	if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
	  try {
		  result = memberService.memberChange(memberFormDto.getEmail(), memberFormDto.getPassword(), passwordEncoder );
	} catch (Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	  if(result == 0) {
		  return new ResponseEntity<String>("비밀번호 변경 완료 되었습니다.", HttpStatus.BAD_REQUEST);
	  }
	  return new ResponseEntity<String>("비밀번호 변경 완료 되었습니다.", HttpStatus.OK);
    }
    
    
    
    private final SessionManager sessionManager;
    
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "auth/login";
    }
	
//	@GetMapping(value = "/member/{memberId}")
//	public String donationDtl(Model model, @PathVariable("donationId") Long DonationId) {
//		return "auth/memberDtl";
//	}
}
