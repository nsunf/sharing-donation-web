//package com.sharingdonation.service;
//
//import javax.persistence.EntityNotFoundException;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import com.sharingdonation.entity.DonationBoard;
//import com.sharingdonation.entity.DonationBoardComment;
//import com.sharingdonation.repository.DonationBoardCommentRepository;
//import com.sharingdonation.repository.DonationBoardRepository;
//
//import lombok.RequiredArgsConstructor;
//
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class DonationBoardCommentService {
//
//	private final DonationBoardCommentRepository donationBoardCommentRepository;
//	private final DonationBoardRepository donationBoardRepository;
//	
//	public Long SaveComment(Long donationBoardId, String comment) throws Exception{
//		DonationBoardComment donationBoardComment = new DonationBoardComment();
//		donationBoardComment.setComment(comment);;
//		DonationBoard donationBoard = donationBoardRepository.findById(donationBoardId).orElseThrow(EntityNotFoundException::new);
//		donationBoardComment.setDonationBoard(donationBoard);
//		
//		donationBoardCommentRepository.save(donationBoardComment);
//		return donationBoardComment.getId();
//		
//	}
//
//}
