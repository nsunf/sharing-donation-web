package com.sharingdonation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.Member;

public interface AdminRepository extends JpaRepository<Member, Long> {

	
	Member findByid (Long memberId);
}
