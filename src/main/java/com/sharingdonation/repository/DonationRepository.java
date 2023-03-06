package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.sharingdonation.entity.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> 
//	QuerydslPredicateExecutor<Donation> 
	, DonationRepositoryCustom 
	{

	List<Donation> findAllByIdIn(List<Long> donatinIdList);
	
	Page<Donation> findByMemberIdAndDelYnOrderByRegTimeDesc(Long memberId, String delYn, Pageable pageable);
	
	List<Donation> findByDone(String done);
	Long countByConfirmYnAndDone(String confirmYn, String done);
}
