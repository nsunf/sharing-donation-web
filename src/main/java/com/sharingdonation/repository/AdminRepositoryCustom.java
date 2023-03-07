package com.sharingdonation.repository;

import com.sharingdonation.dto.MemberAllDto;

public interface AdminRepositoryCustom {
	Long updateAdminByMember(MemberAllDto memberAllDto,Long memberId);
	Long deleteAdminByMember(Long memberId);
	
	Long updateAdminByEneterprice(MemberAllDto memberAllDto, Long memberId);
	Long deleteAdminByEneterprice(Long memberId);
}
