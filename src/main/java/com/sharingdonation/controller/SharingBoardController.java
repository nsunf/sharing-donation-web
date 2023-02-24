package com.sharingdonation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sharingdonation.dto.SharingBoardDto;
import com.sharingdonation.repository.SharingBoardRepository;
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
		model.addAttribute("sharingBoardDto",sharingBoardDto);
		return "sharing/sharedDetail";
	}
	
	//게시글 등록
	@GetMapping("/add_post")
	public String sharedAddPost() {
		return "sharing/sharedDetail";
	}
	
}
