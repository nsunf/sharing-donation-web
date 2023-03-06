package com.sharingdonation.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingBoard;
import com.sharingdonation.entity.SharingBoardComment;

public interface SharingBoardRepository extends JpaRepository <SharingBoard, Long> {
	
	Page<SharingBoard> findBySharingNameContainsOrSubjectContainsOrderByRegTimeDesc (String name, String subject, Pageable pageable);
	
}
