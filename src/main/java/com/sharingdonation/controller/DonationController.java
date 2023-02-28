package com.sharingdonation.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.dto.DonationSearchDto;
import com.sharingdonation.dto.ListDonationDto;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.service.DonationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DonationController {
	
	private final DonationService donationService;
	
	
	//list페이
	@GetMapping(value = "/donation")
	public String donationList(DonationSearchDto donationSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get()-1 : 0, 6);
		Page<ListDonationDto> donationList = donationService.getListDonationPage(donationSearchDto, pageable);
		
		System.out.println(donationList.getContent());
		for(ListDonationDto ln : donationList.getContent()) {
			System.out.println("ln.getGoalPoint():" + ln.getGoalPoint() + ", ln.getPointSum() : " + ln.getPointSum() + ", ln.getHeartCount() : " +ln.getHeartCount());
		}
		model.addAttribute("donationList", donationList);
		model.addAttribute("donationSearchDto", donationSearchDto);
		model.addAttribute("maxPage", 5);
		System.out.println("donation_list_check");
		return "donation/donationList";
	}
	
	@GetMapping(value="/donation/new")
	public String donationForm(Model model) {
		model.addAttribute("donationFormDto", new DonationFormDto());
		return "donation/editDonation";
	}
	
	@PostMapping(value = "/donation/new")
	public String donationNew(@Valid DonationFormDto donationFormDto, BindingResult bindingResult, Model model
			, @RequestParam("donationImgFile") List<MultipartFile> donationImgFileList) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("hassErrors");
			return "donation/editDonation";
		}
		
		if(donationImgFileList.get(0).isEmpty() && donationFormDto.getId() == null) {
			System.out.println("donationImgFile");
			model.addAttribute("errorMessage", "첫버내 상품 이미지는 필수 입력 값 입니다.");
			return "donation/editDonation";
		}
		
		try {
			donationService.saveDonation(donationFormDto, donationImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "기부 등록 중 에러가 발생했습니다.");
			System.out.println("exception");
			return "donation/editDonation";
		}
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/donation/{donationId}")
	public String donationDtl(@PathVariable("donationId") Long DonationId, Model model) {
		try {
			DonationFormDto donationFormDto = donationService.getDonationDtl(DonationId);
			model.addAttribute(donationFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			model.addAttribute("donationFormDto", new DonationFormDto());
			return "donation/editDonation";
		}
		return "donation/editDonation";
	}
	
	//list페이
	@GetMapping(value = "/admin/donation")
	public String adminDonationList(DonationSearchDto donationSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get()-1 : 0, 6);
		Page<Donation> donationList = donationService.getAdminListDonationPage(donationSearchDto, pageable);
		
		System.out.println(donationList.getContent());
		for(Donation ln : donationList.getContent()) {
			System.out.println("ln.getGoalPoint():" + ln.getGoalPoint() + ", ln.getPointSum() : ");
		}
		model.addAttribute("donationList", donationList);
		model.addAttribute("donationSearchDto", donationSearchDto);
		model.addAttribute("maxPage", 5);
		System.out.println("donation_list_check");
		return "donation/donationList";
	}
}
