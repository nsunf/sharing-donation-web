package com.sharingdonation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		List<SharingBoardDto> sharingBoardList = sharingBoardService.getCompletePost();
		model.addAttribute("sharingBoardList",sharingBoardList);
		return "sharing/sharedBoard";
	}
	
	//게시글 등록
	@GetMapping("/add_Post")
	public String sharedAddPost() {
		return "sharing/sharedDetail";
	}
}
