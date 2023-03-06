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
		Member findByMember = adminRepository.findByidAndDelYn(memberId,"N");
		MemberAllDto memberAllDto = MemberAllDto.of(findByMember);
		return memberAllDto;
		
	}
	
	@Transactional
	public Long adminMemberUpdate(MemberAllDto memberAllDto, Long memberId) {
		return adminRepository.updateAdminByMember(memberAllDto, memberId);
	}
	
	@Transactional
	public Long adminMemberDelete(Long memberId) {
		return adminRepository.deleteAdminByMember(memberId);
	}
	
	@Transactional
	public Long adminEnterpriceUpdate(MemberAllDto memberAllDto, Long memberId) {
		return adminRepository.updateAdminByEneterprice(memberAllDto, memberId);
	}
	
	@Transactional
	public Long adminEnterpriceDelete(Long memberId) {
		return adminRepository.deleteAdminByEneterprice(memberId);
	}
	
	
	
	
}
