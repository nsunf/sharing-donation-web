package com.sharingdonation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sharingdonation.dto.SharingAdminSearchDto;
import com.sharingdonation.dto.SharingDto;

public interface SharingRepositoryCustom {
	Page<SharingDto> getAdoptedSharingList(Long memberId, Pageable pageable);
	Page<SharingDto> getAdminSharingDtoList(SharingAdminSearchDto searchDto, Pageable pageable);
}
