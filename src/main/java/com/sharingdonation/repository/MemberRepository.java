package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email); //회원가입시 중복 회원이 있는지 검사하기 위해
	List<Member> findAllByEmailContainsOrNickNameContains(String email, String nickName);
	Member findByEmailAndNameAndCellphone(String email, String name, String cellphone);

}

