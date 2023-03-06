package com.sharingdonation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingBoardComment;
import com.sharingdonation.entity.SharingBoardImg;

public interface SharingBoardCommentRepository extends JpaRepository<SharingBoardComment, Long>{
	List<SharingBoardComment> findBySharingBoardId(Long id);
	
	Long countBySharingBoardId(Long id);
	
	Optional<SharingBoardComment> findById(Long id);
}
