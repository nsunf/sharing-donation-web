package com.sharingdonation.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.Sharing;

public interface SharingRepository extends JpaRepository<Sharing, Long>, SharingRepositoryCustom {
	List<Sharing> findAllByIdIn(List<Long> sharingIdList);
	// delYn | orderBy regTime desc
	Page<Sharing> findAllByDelYnOrderByRegTimeDesc(String delYn, Pageable pageable);
	// detail contains, delYn | orderBy regTime desc 
	Page<Sharing> findAllByDetailContainsAndDelYnOrderByRegTimeDesc(String detail, String delYn, Pageable pageable);
	// detail contains, confirmYn, done, delYn, area.gugun | orderBy regTime desc
	Page<Sharing> findAllByDetailContainsAndConfirmYnAndDoneAndDelYnAndAreaGugunOrderByRegTimeDesc(String detail, String confirmYn, String done, String delYn, String areaName, Pageable pageable);
	// detail contains, confirmYn, done, delYn, area.gugun, category.categoryName | orderBy desc
	Page<Sharing> findAllByDetailContainsAndConfirmYnAndDoneAndDelYnAndAreaGugunAndCategoryCategoryNameOrderByRegTimeDesc(String detail, String confirmYn, String done, String delYn, String areaName, String categoryName, Pageable pageable);

//	Page<Sharing> findAllByConfirmYnAndDelYnAndAreaGugunOrderByRegTimeDesc(String confirmYn, String delYn, String areaName, Pageable pageable);

	List<Sharing> findByMemberId(Long memberId);
	// name contains, delYn | orderBy regTime desc
	Page<Sharing> findByNameContainsAndDelYnOrderByRegTimeDesc(String name, String delYn, Pageable pageable);
	// detail contains, delYn | orderBy regTime desc
	Page<Sharing> findByDetailContainsAndDelYnOrderByRegTimeDesc(String detail, String delYn, Pageable pageable);
	// member.id | orderBy regTime desc
	Page<Sharing> findByMemberIdOrderByRegTimeDesc(Long memberId, Pageable pageable);
	// member.id, delYn | orderBy regTime desc
	Page<Sharing> findByMemberIdAndDelYnOrderByRegTimeDesc(Long memberId, String delYn, Pageable pageable);

	Long countByDone(String done);
	Long countByConfirmYnAndDone(String confirmYn, String done);
}
