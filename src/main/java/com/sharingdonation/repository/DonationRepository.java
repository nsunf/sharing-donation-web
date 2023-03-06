package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.sharingdonation.entity.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long>, 
	QuerydslPredicateExecutor<Donation> 
	, DonationRepositoryCustom 
	{

	List<Donation> findByDone(String done);
	
}
