package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sharingdonation.dto.SharingStoryDto;
import com.sharingdonation.dto.StoryAdminSearchDto;
import com.sharingdonation.dto.StoryDto;

public interface StoryRepositoryCustom {
	Page<SharingStoryDto> getAdminStoryPage(StoryAdminSearchDto searchDto, Pageable pageable);
	List<StoryDto> getAdminStoryList(Long sharingId);
	StoryDto getStoryDto(Long storyId);
}
