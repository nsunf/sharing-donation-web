package com.sharingdonation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharingdonation.dto.MemberAllDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

	private final AdminRepository adminRepository;
	
	public MemberAllDto findByMemberId(Long memberId) {
		Member findByMember = adminRepository.findByid(memberId);
		MemberAllDto memberAllDto = MemberAllDto.of(findByMember);
		return memberAllDto;
		
	}
	
	
}
