package com.sharingdonation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingBoardHeart;

public interface SharingBoardHeartRepository extends JpaRepository<SharingBoardHeart, Long> {
	Optional<SharingBoardHeart> findBySharingBoardIdAndMemberId(Long sharingBoard_id, Long member_id);
	Long countBySharingBoardId (Long sharingBoard_id);
}
