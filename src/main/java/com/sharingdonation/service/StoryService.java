package com.sharingdonation.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

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
	
	public StoryFormDto getStoryFormDto(Long sharingId, Long memberId) {
		Story findStory = storyRepo.findBySharingIdAndMemberId(sharingId, memberId);
		return new StoryFormDto(sharingId, memberId, findStory == null ? "N" : "Y");
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
}
