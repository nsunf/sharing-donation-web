package com.sharingdonation.controller;

import java.time.format.DateTimeFormatter;
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

import com.sharingdonation.dto.DonationBoardCommentDto;
import com.sharingdonation.dto.DonationBoardDto;
import com.sharingdonation.dto.DonationBoardFormDto;
import com.sharingdonation.dto.DonationBoardImgDto;
import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.dto.SharingBoardCommentDto;
import com.sharingdonation.service.DonationBoardImgService;
import com.sharingdonation.service.DonationBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DonationBoardController {
	
	private final DonationBoardService donationBoardService;
	private final DonationBoardImgService donationBoardImgService;
	//show the donated board create page
	
	@GetMapping(value = "/donatedBoard/edit")
	public String addDonatedBoard(Model model) { 
		model.addAttribute("donations", donationBoardService.getDonationBorardSelect());
		model.addAttribute("donationBoardFormDto", new DonationBoardFormDto());
		return "admin/editDonatedBoard"; 
	}
	
	
	//add donationBoard
	@PostMapping(value = "/donatedBoard/edit")
	public String donatedBoardCreate(@Valid DonationBoardFormDto donationBoardFormDto, BindingResult bindingResult, 
			Model model, List<MultipartFile> donationBoardImgFileList) {
				System.out.println("++++++++++");
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().stream().forEach(e -> {
				System.out.println(e);
			});
			return "admin/editDonatedBoard";
		}
		
		if(donationBoardImgFileList.isEmpty() && donationBoardFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
			return "admin/editDonatedBoard";
		}
		
		try {
			donationBoardService.SaveDonationBoard(donationBoardFormDto, donationBoardImgFileList);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "An error occurred while adding a gamer.");
			return "admin/editDonatedBoard";
		}
		
		return "redirect:/donatedBoard";
		
	}
	
	//show donated board list page
	@GetMapping(value = "/donatedBoard")
	public String donatedBoard(Optional<Integer> page, Model model) { 
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<DonationBoardDto> donationBoards = donationBoardService.getDonationBoardDtoPage(pageable);
		
		model.addAttribute("donationBoards",donationBoards);
		model.addAttribute("maxPage", 5);
		return "donation/donatedList";
		
	}
	
	
	//show donated board detail page
	@GetMapping(value = "/donatedBoard/{donationBoardId}") //{donationBoardId}
	public String donatedBoardDetail(Model model, @PathVariable("donationBoardId") Long donationBoardId) { //Model model, @PathVariable("donationBoardId") Long donationBoardId
		
		DonationBoardFormDto donationBoardFormDto = donationBoardService.getdonationBoardDetail(donationBoardId);
		System.out.println("donatedBoardDetail donationBoardFormDto");
		
		List<DonationBoardCommentDto> donationBoardCommentDtoList = donationBoardService.getBoardCommentList(donationBoardId);
		
		List<DonationBoardImgDto> donationBoardImgDtos = donationBoardFormDto.getDonationBoardImgDtoList();
		
		for(DonationBoardImgDto p : donationBoardImgDtos) {
			System.out.println("ccc:" + p.getImgUrl());
		}

		System.err.println(donationBoardFormDto.getDonationBoardSelectDto().getId());
//		model.addAttribute("donations", donationBoardService.getDonationBorardSelect());
		model.addAttribute("donationBoardCommentDtoList",donationBoardCommentDtoList);
		model.addAttribute("donationBoard",donationBoardFormDto);
		//model.addAttribute("commentFormDto", new CommentFormDto());
		
		
		return "donation/donatedBoardDetail";
		
	}
	
	//댓글 등록
		@PostMapping(value="/donatedBoard/{donationBoardId}/comment")
		public String insertComment(@PathVariable("donationBoardId") Long id, @RequestParam String comment, Model model) {
													//가짜 데이터 member_id 넣었음
			donationBoardService.insertComment(1L, id, comment);
			
			return "redirect:/donatedBoard/{donationBoardId}/"+id;
		}
	
}
