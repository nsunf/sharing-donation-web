package com.sharingdonation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.sharingdonation.entity.Story;

public interface StoryRepository
		extends JpaRepository<Story, Long>, QuerydslPredicateExecutor<Story>, StoryRepositoryCustom {

	// 채택된 사연 회원 닉네임을 찾음
	Story findBySharingIdAndChooseYn(Long sharingId, String chooseYn);

	Story findByIdAndChooseYn(Long id, String chooseYn);

	Story findBySharingIdAndMemberId(Long sharingId, Long memberId);

}
