package com.sharingdonation.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sharingdonation.constant.Role;
import com.sharingdonation.dto.MemberAllDto;
import com.sharingdonation.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminService;
	
	@GetMapping("/admin/management/{memberId}")
	public String adminManagement(@PathVariable("memberId") Long memberId, Model model) {
		MemberAllDto memberAllDto = adminService.findByMemberId(memberId);
			 model.addAttribute("member",memberAllDto);
 
			 if (memberAllDto.getRole().equals(Role.USER)) {
				 return "/admin/MemberManagement";
			}if(memberAllDto.getRole().equals(Role.COM)) {
				return "/admin/EnterpriceManagement";
			}
			  
			return "/";
			 
		 
 
	}
	
	@PostMapping("/admin/management/update/{memberId}")
	public @ResponseBody ResponseEntity updateAdminManagement(@RequestBody  @Valid MemberAllDto memberAllDto,BindingResult bindingResult,@PathVariable("memberId") Long memberId  ) {
		if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
	  try {
		  Long result = adminService.adminMemberUpdate(memberAllDto, memberId);
	} catch (Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	 return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}
	
	@PostMapping("/admin/management/delete/{memberId}")
	public @ResponseStatus ResponseEntity deleteAdminManagement(@PathVariable("memberId") Long memberId) {
		Long result = adminService.adminMemberDelete(memberId);
		return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}
	
	@GetMapping("/admin/enterpriceManagement/{memberId}")
	public String adminEneterpriceManagement(@PathVariable("memberId") Long memberId,Model model) {
		MemberAllDto memberAllDto = adminService.findByMemberId(memberId);
		 model.addAttribute("member",memberAllDto);
		 return "/admin/EnterpriceManagement";
	}
	
	@PostMapping("/admin/enterpriceManagement/update/{memberId}")
	public @ResponseBody ResponseEntity adminEneterpriceUpdate(@RequestBody  @Valid MemberAllDto memberAllDto,BindingResult bindingResult,@PathVariable("memberId") Long memberId  ) {
		if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
	  try {
		  Long result = adminService.adminEnterpriceUpdate(memberAllDto, memberId);
	} catch (Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	 return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}
	
	@PostMapping("/admin/enterpriceManagement/delete/{memberId}")
	public @ResponseStatus ResponseEntity adminEneterpriceDelete(@PathVariable("memberId") Long memberId) {
		Long result = adminService.adminEnterpriceDelete(memberId);
		return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}
	  
	

	
}
