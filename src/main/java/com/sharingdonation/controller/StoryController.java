package com.sharingdonation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharingdonation.dto.SharingDto;
import com.sharingdonation.dto.SharingStoryDto;
import com.sharingdonation.dto.StoryDto;
import com.sharingdonation.dto.StoryFormDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Story;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.service.SharingService;
import com.sharingdonation.service.StoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StoryController {
	
	private final MemberRepository memberRepo;
	private final StoryService storyService;
	private final SharingService sharingService;
	ObjectMapper mapper = new ObjectMapper();

	// 사연 등록
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("/story/new")
	public @ResponseBody ResponseEntity<?> addStory(@RequestBody Map<String, Object> map) {
		
		StoryFormDto storyFormDto = mapper.convertValue(map, StoryFormDto.class);
		Long storyId = storyService.addStory(storyFormDto);
		
		return new ResponseEntity<Long>(storyId, HttpStatus.OK);
	}

	// 사연 수정 모달
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("/story/{id}")
	public @ResponseBody ResponseEntity<?> getStory(@PathVariable("id") Long storyId) {
		StoryFormDto storyFormDto = storyService.getStoryFormDto(storyId);
		
		return new ResponseEntity<StoryFormDto>(storyFormDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("/story/sharingid/{id}")
	public @ResponseBody ResponseEntity<?> getStoryBySharingId(@PathVariable("id") Long sharingId) {
		StoryFormDto storyFormDto = storyService.getStoryFormDtoBySharingId(sharingId);
		
		return new ResponseEntity<StoryFormDto>(storyFormDto, HttpStatus.OK);
	}
	
	// 사연 수정
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("/story/edit")
	public String editStory(StoryFormDto storyFormDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		Member member = memberRepo.findByEmail(email);
		boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		boolean isAuthor = storyFormDto.getMemberId() == member.getId();

		if (isAdmin || isAuthor)
			storyService.updateStory(storyFormDto);

		return "redirect:/admin/story/id/" + storyFormDto.getId();
	}
	
	// 사연 채택
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/story/adopt/{storyId}")
	public @ResponseBody ResponseEntity<?> adoptStory(@PathVariable Long storyId) {
		Story story = storyService.adoptStory(storyId);
		return new ResponseEntity<Integer>(story == null ? 0 : 1, HttpStatus.OK);
	}
	
	// 사연 삭제
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/story/delete")
	public @ResponseBody ResponseEntity<?> deleteStory(@RequestBody Map<String, Object> map) {
		List<Long> storyIdList = new ArrayList<>();
		Object obj = map.get("storyIdList");

		Long count = 0L;
		if (obj instanceof List<?>) {
			List<String> list = (List<String>) obj;
			storyIdList = list.stream().mapToLong(Long::valueOf).boxed().toList();
			count = storyService.deleteStories(storyIdList);
		}
		
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	
	// 관리자 스토리 관리 페이지
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = {"/admin/story", "/admin/story/{search}"})
	public String storyMngPage(@PathVariable Optional<String> search, @RequestParam Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.orElse(0), 10);
		Page<SharingStoryDto> sharingStoryDtoList =storyService.getAdminSharingStoryPage(search.orElse(""), pageable); 
		
		model.addAttribute("searchTerm", search.orElse(""));
		model.addAttribute("sharingStoryDtoList", sharingStoryDtoList);
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);
		return "admin/storyList";
	}
	
	// 관리자 나눔 상품별 사연 상세 페이지
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin/story/id/{sharingId}")
	public String storyDetail(@PathVariable Long sharingId, Model model) {
		SharingDto sharingDto = sharingService.getSharingDto(sharingId);
		List<StoryDto> storyDtoList = storyService.getAdminSharingDetail(sharingId);
		
		model.addAttribute("sharingDto", sharingDto);
		model.addAttribute("storyDtoList", storyDtoList);

		return "admin/storyDetail";
	}
}
