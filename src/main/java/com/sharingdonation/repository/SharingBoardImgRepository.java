package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingBoardImg;

public interface SharingBoardImgRepository extends JpaRepository<SharingBoardImg, Long> {
	List<SharingBoardImg> findBySharingBoardId(Long sharingBoard_id);
	
	SharingBoardImg findBySharingBoardIdAndRepimgYn(Long sharingBoard_id, String repimgYn);
	
}
