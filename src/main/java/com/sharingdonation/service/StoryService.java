package com.sharingdonation.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sharingdonation.dto.SharingStoryDto;
import com.sharingdonation.dto.StoryDto;
import com.sharingdonation.dto.StoryFormDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.entity.Story;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.SharingRepository;
import com.sharingdonation.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryService {
	private final StoryRepository storyRepo;
	private final SharingRepository sharingRepo;
	private final MemberRepository memberRepo;
	
	public StoryDto getStoryDto(Long storyId) {
		return storyRepo.getStoryDto(storyId);
	}
	
	public StoryFormDto getStoryFormDto(Long sharingId, Long memberId) {
		Story findStory = storyRepo.findBySharingIdAndMemberId(sharingId, memberId);
		return new StoryFormDto(sharingId, memberId, findStory == null ? "N" : "Y");
	}
	
	public StoryFormDto getStoryFormDto(Long storyId) {
		Story story = storyRepo.findById(storyId).orElseThrow(EntityNotFoundException::new);
		return new StoryFormDto(story);
	}

	public StoryFormDto getStoryFormDtoBySharingIdAndEmail(Long sharingId, String email) {
		Member member = memberRepo.findByEmail(email);
		Story story = storyRepo.findBySharingIdAndMemberId(sharingId, member.getId());
		return new StoryFormDto(story);
	}

	public Long addStory(StoryFormDto formDto) {
		Sharing sharing = sharingRepo.findById(formDto.getSharingId()).orElseThrow(EntityNotFoundException::new);
		Member member = memberRepo.findById(formDto.getMemberId()).orElseThrow(EntityNotFoundException::new);
		
		Story findStory = storyRepo.findBySharingIdAndMemberId(sharing.getId(), member.getId());
		if (findStory != null) return null;

		Story story = new Story();
		story.setSharing(sharing);
		story.setMember(member);
		story.setContent(formDto.getContent());
		story.setRegTime(LocalDateTime.now());
		story.setChooseYn("N");
		
		storyRepo.save(story);
		return story.getId();
	}
	
	public Story updateStory(StoryFormDto storyFormDto) {
		Story story = storyRepo.findById(storyFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		story.setContent(storyFormDto.getContent());
		return story;
	}
	
	public Story adoptStory(Long storyId) {
		Story story = storyRepo.findById(storyId).orElseThrow(EntityNotFoundException::new);
		story.setChooseYn("Y");
		story.getSharing().setDone("Y");
		story.getSharing().setUpDateTime(LocalDateTime.now());
		return story;
	}
	
	public Long deleteStories(List<Long> storyIdList) {
		Long count = 0L;
		for (int i = 0; i < storyIdList.size(); i++) {
			Story story = storyRepo.findById(storyIdList.get(i)).orElseThrow(EntityNotFoundException::new);
			story.setDelYn("Y");
			count++;
		}
		
		return count;
	}

	public Page<SharingStoryDto> getAdminSharingStoryPage(String search, Pageable pageable) {
		return storyRepo.getAdminStoryPage(search, pageable);
	}
	
	public List<StoryDto> getAdminSharingDetail(Long sharingId) {
		return storyRepo.getAdminStoryList(sharingId);
	}
}
