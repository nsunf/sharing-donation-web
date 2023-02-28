package com.sharingdonation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.entity.Member;

public interface MyPageRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, MyPageRepositoryCustom{

	
	
	
}
