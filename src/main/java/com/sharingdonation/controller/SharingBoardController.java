package com.sharingdonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shared_board")
public class SharingBoardController {
	
	//게시판 화면 띄워줌
	@GetMapping("/complete")
	public String shared_board() {
		return "sharing/sharedBoard";
	}
}
