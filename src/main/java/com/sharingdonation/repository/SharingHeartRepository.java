package com.sharingdonation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingHeart;

public interface SharingHeartRepository extends JpaRepository<SharingHeart, Long> {
	Optional<SharingHeart> findBySharingIdAndMemberId(Long sharingId, Long memberId);
	Long countBySharingId(Long sharingId);
}
