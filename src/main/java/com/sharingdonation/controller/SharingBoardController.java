package com.sharingdonation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingdonation.dto.SharingBoardDto;
import com.sharingdonation.serviece.SharingBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shared_board")
public class SharingBoardController {
	
	private final SharingBoardService sharingBoardService;
	
	//게시판 화면 띄워줌
	@GetMapping("")
	public String shared_board(Model model) {
		List<SharingBoardDto> sharingBoardList = sharingBoardService.getCompletePost();
		model.addAttribute("sharingBoardList",sharingBoardList);
		return "sharing/sharedBoard";
	}
}
