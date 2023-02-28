package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.DonationBoardComment;



public interface DonationBoardCommentRepository extends JpaRepository<DonationBoardComment, Long>{

List<DonationBoardComment> findByDonationBoardId(Long id);
	
	Long countByDonationBoardId(Long id);

}
