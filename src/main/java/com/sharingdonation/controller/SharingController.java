package com.sharingdonation.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.SharingFormDto;
import com.sharingdonation.serviece.AreaService;
import com.sharingdonation.serviece.CategoryService;
import com.sharingdonation.serviece.SharingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sharing")
@RequiredArgsConstructor
public class SharingController {
	
	private final SharingService sharingService;
	private final AreaService areaService;
	
	@GetMapping("")
	public String sharingList() {
		return "sharing/sharingList";
	}
	
	@GetMapping("/create")
	public String createSharingForm(Model model) {
		model.addAttribute("sharingFormDto", new SharingFormDto());
		model.addAttribute("areaDtoList", areaService.getAreaList());
		return "sharing/editSharing";
	}
	
	@PostMapping("/create")
	public String createSharing(@Valid SharingFormDto sharingFormDto, BindingResult bindingResult, List<MultipartFile> sharingImgFileList) {
		
		if (bindingResult.hasErrors()) {
			return "sharing/editSharing";
		}
		
		try {
			sharingService.saveSharing(sharingFormDto, sharingImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "sharing/sharingList";
	}
	
	// MypageController로 이동 필요
	@GetMapping("/mypage")
	public String mypageSharingList() {
		return "mypage/registeredShareList";
	}
}
