package com.sharingdonation.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingHeart;

public interface SharingHeartRepository extends JpaRepository<SharingHeart, Long> {
	Optional<SharingHeart> findBySharingIdAndMemberId(Long sharingId, Long memberId);
	Long countBySharingId(Long sharingId);

	Page<SharingHeart> findBySharingNameContainsOrderByRegTimeDesc(String name, Pageable pageable);
	Page<SharingHeart> findByMemberNickNameContainsOrderByRegTimeDesc(String nickName, Pageable pageable);
}
