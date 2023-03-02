package com.sharingdonation.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


import com.sharingdonation.entity.DonationBoard;

public interface DonationBoardRepository extends JpaRepository<DonationBoard, Long>, QuerydslPredicateExecutor<DonationBoard>, DonationBoardRepositoryCustom{


	
	
}
