package com.sharingdonation.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.Sharing;

public interface SharingRepository extends JpaRepository<Sharing, Long> {
	Page<Sharing> findAllByDelYnOrderByRegTimeDesc(String delYn, Pageable pageable);
	Page<Sharing> findAllByDetailContainsAndDelYnOrderByRegTimeDesc(String detail, String delYn, Pageable pageable);
	Page<Sharing> findAllByDetailContainsAndConfirmYnAndDelYnAndAreaGugunOrderByRegTimeDesc(String detail, String confirmYn, String delYn, String areaName, Pageable pageable);
	Page<Sharing> findAllByDetailContainsAndConfirmYnAndDelYnAndAreaGugunAndCategoryCategoryNameOrderByRegTimeDesc(String detail, String confirmYn, String delYn, String areaName, String categoryName, Pageable pageable);
//	Page<Sharing> findAllByConfirmYnAndDelYnAndAreaGugunOrderByRegTimeDesc(String confirmYn, String delYn, String areaName, Pageable pageable);
	List<Sharing> findAllByIdIn(List<Long> sharingIdList);
	Page<Sharing> findByNameContainsAndDelYnOrderByRegTimeDesc(String name, String delYn, Pageable pageable);
	Page<Sharing> findByDetailContainsAndDelYnOrderByRegTimeDesc(String detail, String delYn, Pageable pageable);
	Page<Sharing> findByMemberIdOrderByRegTimeDesc(Long memberId, Pageable pageable);
	Page<Sharing> findByMemberIdAndDelYnOrderByRegTimeDesc(Long memberId, String delYn, Pageable pageable);
	List<Sharing> findByMemberId(Long memberId);
}
