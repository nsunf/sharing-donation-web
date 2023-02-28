package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.sharingdonation.entity.DonationBoard;

public interface DonationBoardRepository extends JpaRepository<DonationBoard, Long>, QuerydslPredicateExecutor<DonationBoard>, DonationBoardRepositoryCustom{
	
}
