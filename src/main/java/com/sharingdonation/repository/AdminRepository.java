package com.sharingdonation.repository;
 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.sharingdonation.entity.Member;

public interface AdminRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, AdminRepositoryCustom {
	Member findByidAndDelYn (Long memberId, String delYn);
}
