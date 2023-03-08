package com.sharingdonation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sharingdonation.dto.MemberAllDto;
import com.sharingdonation.dto.SearchDto;

public interface AdminRepositoryCustom {
	Long updateAdminByMember(MemberAllDto memberAllDto,Long memberId);
	Long deleteAdminByMember(Long memberId);
	
	Long updateAdminByEneterprice(MemberAllDto memberAllDto, Long memberId);
	Long deleteAdminByEneterprice(Long memberId);
	
	Page<MemberAllDto> getAdminMemberList(SearchDto searchDtom, Pageable pageable);
}
