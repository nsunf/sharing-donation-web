package com.sharingdonation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingBoardHeart;

public interface SharingBoardHeartRepository extends JpaRepository<SharingBoardHeart, Long> {
	Optional<SharingBoardHeart> findBySharingBoardIdAndMemberId(Long sharingBoard_id, Long member_id);
	Long countBySharingBoardId (Long sharingBoard_id);
	List<SharingBoardHeart> findBySharingBoardId(Long sharingBoardId);
	
	Page<SharingBoardHeart> findBySharingBoardSubjectContainsOrderByRegTimeDesc(String subject, Pageable pageable);
	Page<SharingBoardHeart> findByMemberNickNameContainsOrderByRegTimeDesc(String nickName, Pageable pageable);
}
