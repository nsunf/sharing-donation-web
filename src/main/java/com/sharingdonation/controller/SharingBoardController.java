package com.sharingdonation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sharingdonation.dto.SharingBoardCommentDto;
import com.sharingdonation.dto.SharingBoardDto;
import com.sharingdonation.service.SharingBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sharing_board")
public class SharingBoardController {
	
	private final SharingBoardService sharingBoardService;
	
	//게시판 화면 띄워줌
	@GetMapping("")
	public String sharingBoard(Model model) {
		List<SharingBoardDto> sharingBoardList = sharingBoardService.getCompletePostList();
		model.addAttribute("sharingBoardList",sharingBoardList);
		return "sharing/sharedBoard";
	}
	
	//게시글 보기
	@GetMapping(value = "/view/{shared_post_id}")
	public String ViewSharedPost(Model model, @PathVariable("shared_post_id") Long id) {
		SharingBoardDto sharingBoardDto = sharingBoardService.getCompletePost(id);
		List<SharingBoardCommentDto> sharingBoardCommentDtoList = sharingBoardService.getBoardCommentList();
		model.addAttribute("sharingBoardDto",sharingBoardDto);
		model.addAttribute("sharingBoardCommentDtoList",sharingBoardCommentDtoList);
		return "sharing/sharedDetail";
	}
	
	
}
