//package com.sharingdonation.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.sharingdonation.service.DonationBoardCommentService;
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor
//public class DonationBoardCommentController {
//		private DonationBoardCommentService donationBoardCommentService;
//		
//		@PostMapping(value = "donationBoard/{donationBoardId}/comment")
//		public @ResponseBody ResponseEntity<?> commentCreate(@PathVariable("donationBoardId") Long donationBoardIdPath, Long donationBoardId, String comment, Model model){
//			
//
//			try {
//				donationBoardCommentService.SaveComment(donationBoardId, comment);
//			} catch (Exception e) {
//				e.printStackTrace();
//			
//			}
//			return new ResponseEntity<String>("Halo", HttpStatus.OK);
//			
//		}
//		
//		
//		}
