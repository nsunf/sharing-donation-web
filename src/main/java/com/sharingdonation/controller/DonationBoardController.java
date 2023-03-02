package com.sharingdonation.controller;

//import java.time.*;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.sharingdonation.dto.DonationBoardCommentDto;
import com.sharingdonation.dto.DonationBoardDto;
import com.sharingdonation.dto.DonationBoardFormDto;
import com.sharingdonation.dto.DonationBoardImgDto;
import com.sharingdonation.dto.DonationBoardSearchDto;
import com.sharingdonation.dto.DonationBoardSelectDto;
import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.dto.SharingBoardCommentDto;
import com.sharingdonation.entity.DonationBoard;
import com.sharingdonation.entity.DonationBoardComment;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.service.DonationBoardHeartService;
import com.sharingdonation.service.DonationBoardImgService;
import com.sharingdonation.service.DonationBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DonationBoardController {
	
	private final DonationBoardService donationBoardService;
	private final DonationBoardImgService donationBoardImgService;
	private final DonationBoardHeartService donationBoardHeartService;
	private final MemberRepository memberRepository;
	
	//show the donated board create page
	
	@GetMapping(value = "/admin/donatedBoard/edit")
	public String addDonatedBoard(Model model) { 
		model.addAttribute("donations", donationBoardService.getDonationBorardSelect());
		model.addAttribute("donationBoardFormDto", new DonationBoardFormDto());
		return "admin/editDonatedBoard"; 
	}
	
	
	//add donationBoard
	@PostMapping(value = "/admin/donatedBoard/edit")
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
		
		return "redirect:/admin/donatedBoard";
		
	}
	
	//show donated board list pages
		@GetMapping(value = {"/admin/donatedBoards", "/admin/donatedBoards/{page}"})
		public String AdmindonatedBoard(DonationBoardSearchDto donationBoardSearchDto, @PathVariable("page") Optional<Integer> page, Model model) { 
			
			
			Pageable pageable = PageRequest.of(page.isPresent() ? page.get()-1 : 0, 6);
			
			Page<DonationBoardDto> donationBoards = donationBoardService.getDonationBoardDtoPage(donationBoardSearchDto, pageable);
		
			int nowPage = (page.isPresent()) ? page.get() : 1;
			model.addAttribute("donationBoards",donationBoards);
			//model.addAttribute("donations", donationBoardService.getDonationBorardSelect());
			model.addAttribute("donationBoardSearchDto", donationBoardSearchDto);
			model.addAttribute("maxPage", 5);
			model.addAttribute("rowPerPage", 6 );
			model.addAttribute("pages", nowPage);
			
			
			return "admin/donatedList";
			
		}
	
	//show donated board list page
	@GetMapping(value = {"/donatedBoards", "/donatedBoards/{page}"})
	public String donatedBoard(DonationBoardSearchDto donationBoardSearchDto, @PathVariable("page") Optional<Integer> page, Model model) { 
		//System.out.println(" controller donatedBoard page -- " +   page.get());
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get()-1 : 0, 6);
		
		System.out.println(" donatedBoard Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);");
		Page<DonationBoardDto> donationBoards = donationBoardService.getDonationBoardDtoPage(donationBoardSearchDto, pageable);
		
		model.addAttribute("donationBoards",donationBoards);
		model.addAttribute("donationBoardSearchDto", donationBoardSearchDto);
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
		
//		for(DonationBoardImgDto p : donationBoardImgDtos) {
//			System.out.println("ccc:" + p.getImgUrl());
//		}

//		System.err.println(donationBoardFormDto.getDonationBoardSelectDto().getId());
//		model.addAttribute("donations", donationBoardService.getDonationBorardSelect());
		model.addAttribute("donationBoardCommentDtoList",donationBoardCommentDtoList);
		
//		donationBoardFormDto.get
//		for(DonationBoardFormDto f : donationBoardFormDto ) {
//			
//		}
		model.addAttribute("donationBoard",donationBoardFormDto);
		model.addAttribute("donationBoardHeartDto", donationBoardHeartService.getDonationBoardHeartCount(donationBoardId));
		model.addAttribute("donationBoardHeartCount", donationBoardHeartService.getDonationBoardHeartCount(donationBoardId));
//		model.addAttribute("localDateTime",LocalDateTime.now());
//		model.addAttribute("df",DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		//model.addAttribute("commentFormDto", new CommentFormDto());
		
		
		return "donation/donatedBoardDetail";
		
	}
	
	//댓글 등록
		@PostMapping(value="/donatedBoard/{donationBoardId}/comment")
		public String insertComment(@PathVariable("donationBoardId") Long id, @RequestParam String comment, Model model) {
													//가짜 데이터 member_id 넣었음
			donationBoardService.insertComment(1L, id, comment);
			
			return "redirect:/donatedBoard/" + id;
		}
		
	//댓글 삭제 
		@DeleteMapping("/donatedBoard/{id}/delete")
	    public @ResponseBody ResponseEntity<?> deleteComment(@PathVariable ("id") Long id,  Model model){
			System.out.println("/donationBoard/{donationBoardId}/delete");
			System.out.println("commentId = " + id);
	        donationBoardService.deleteComment(id);

	        return new ResponseEntity<Long> (id, HttpStatus.OK);
	    }
		
	//좋아요
		@GetMapping("/donatedBoard/heart/{id}")
		public  @ResponseBody ResponseEntity<?> toggleDonationBoardHeart(@PathVariable Long id){
			donationBoardHeartService.toggleDonationBoardHeart(id);
			Long donationBoardheartCount = donationBoardHeartService.getDonationBoardHeartCount(id);
			return new ResponseEntity<Long>(donationBoardheartCount, HttpStatus.OK);
		}
		
	
	//doantionBoard modify view
		@GetMapping(value = "/admin/donatedBoard/{donationBoardId}")
		public String donatedBoardDetail(@PathVariable("donationBoardId") Long donationBoardId, Model model) {
			try {
				DonationBoardFormDto donationBoardFormDto = donationBoardService.getdonationBoardDetail(donationBoardId);   //get detail
				model.addAttribute(donationBoardFormDto);
				model.addAttribute("donations", donationBoardService.getDonationBorardSelect());  //get select list
			} catch (EntityNotFoundException e) {
				model.addAttribute("errorMessage","This danatioBoard that dose not exist." );
				model.addAttribute("donationBoardFormDto", new DonationBoardFormDto());
				return "admin/editDonatedBoard";
			}
			
			return "admin/editDonatedBoard";
			
		}
		
		
		//doantionBoard modify
		@PostMapping(value = "/admin/donatedBoard/{donationBoardId}")
		public String updateDonationBoard(@Valid DonationBoardFormDto donationBoardformDto, BindingResult bindingResult,
				Model model, @RequestParam("donationBoardImgFile") List<MultipartFile> donationBoardImgFileList) {
			
			
			System.out.println("i updateDonationBoard )");
			if(bindingResult.hasErrors()) {
				System.out.println("i updateDonationBoard ndingResult.hasErrors()");
				return "admin/editDonatedBoard";
			}
			
			
			if(donationBoardImgFileList.get(0).isEmpty() && donationBoardformDto.getId() == null) {
				System.out.println("i updateDonationBoard onationBoardImgFileList.get(0).isEmpty() && donationBo");
				model.addAttribute("errorMessage", "Must enter first danation board image.");
				return "admin/editDonatedBoard";
			}
			
			try {
				System.out.println("Controller donationBoardformDto.getId()  === " + donationBoardformDto.getId() );
				donationBoardService.donationBoardUpdate(donationBoardformDto, donationBoardImgFileList);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("i updateDonationBoard Exception");
				model.addAttribute("errorMessage", "An error occurred while modifying.");
				return "admin/editDonatedBoard";
			}
			return "redirect:/admin/donatedBoard";
			
		}
		
		
		
	//doantionBoard delete
		@DeleteMapping(value= "/admin/donatedBoard/{donationBoardId}/delete")
		public @ResponseBody ResponseEntity<?> deleteDonationBoard(@PathVariable("donationBoardId") Long donationBoardId, Model model) {
			System.out.println("/donationBoard/{donationBoardId}/delete");
			try {
				donationBoardImgService.deleteDonationBoardImg(donationBoardId);
				donationBoardService.deleteDonationBoard(donationBoardId);
				donationBoardHeartService.deleteDonationBoardHeart(donationBoardId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<Long>(donationBoardId,HttpStatus.OK);
		}
		
	
}
