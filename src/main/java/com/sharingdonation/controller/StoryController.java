package com.sharingdonation.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.sharingdonation.dto.StoryAdminSearchDto;
import com.sharingdonation.dto.StoryDto;
import com.sharingdonation.dto.StoryFormDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Story;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.service.PointService;
import com.sharingdonation.service.SharingService;
import com.sharingdonation.service.StoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StoryController {
	
	private final MemberRepository memberRepo;
	private final StoryService storyService;
	private final SharingService sharingService;
	private final PointService pointService;
	ObjectMapper mapper = new ObjectMapper();

	// 사연 등록
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("/story/new")
	public @ResponseBody ResponseEntity<?> addStory(@RequestBody Map<String, Object> map, Principal principal) {
		
		StoryFormDto storyFormDto = mapper.convertValue(map, StoryFormDto.class);
		System.out.println(storyFormDto);
		Long storyId = storyService.addStory(storyFormDto);
		
		
		if (storyId == null)
			return new ResponseEntity<Long>(storyId, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<Long>(storyId, HttpStatus.OK);
	}

	// 사연 수정 모달
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("/story/{id}")
	public @ResponseBody ResponseEntity<?> getStory(@PathVariable("id") Long storyId) {
		StoryFormDto storyFormDto = storyService.getStoryFormDto(storyId);
		
		return new ResponseEntity<StoryFormDto>(storyFormDto, HttpStatus.OK);
	}
// 내가 등록한 사연
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("/story/sharingid/{id}")
	public @ResponseBody ResponseEntity<?> getStoryBySharingId(@PathVariable("id") Long sharingId, Principal principal) {
		StoryFormDto storyFormDto = storyService.getStoryFormDtoBySharingIdAndEmail(sharingId, principal.getName());
		
		return new ResponseEntity<StoryFormDto>(storyFormDto, HttpStatus.OK);
	}
	
	// 사연 수정
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/story/edit")
	public String editStory(StoryFormDto storyFormDto) {
		storyService.updateStory(storyFormDto);

		return "redirect:/admin/story/id/" + storyFormDto.getId();
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/mypage/story/edit")
	public String editMyStory(StoryFormDto storyFormDto, Principal principal) {
		String email = principal.getName();
		Member member = memberRepo.findByEmail(email);
		if (storyFormDto.getMemberId() == member.getId())
			storyService.updateStory(storyFormDto);
		return "redirect:/mypage/shared";
	}
	
	// 사연 채택
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/story/adopt/{storyId}")
	public @ResponseBody ResponseEntity<?> adoptStory(@PathVariable Long storyId) {
		Story story = storyService.adoptStory(storyId);
		boolean storyAdopted = story != null;
		
		if (storyAdopted) {
			pointService.saveSharingPoint(story);
		}
		
		return new ResponseEntity<Integer>(storyAdopted ? 1 : 0, HttpStatus.OK);
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
			storyIdList = list.stream().mapToLong(Long::valueOf).boxed().collect(Collectors.toList());
			count = storyService.deleteStories(storyIdList);
		}
		
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	
	// 관리자 스토리 관리 페이지
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin/story")
	public String storyMngPage(StoryAdminSearchDto searchDto, @RequestParam Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.orElse(0), 10);
		
		StoryAdminSearchDto _searchDto = searchDto == null ? new StoryAdminSearchDto() : searchDto;
		
		model.addAttribute("sharingStoryDtoList", storyService.getAdminSharingStoryPage(_searchDto, pageable));
		model.addAttribute("searchDto", searchDto);
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
	
	// redirect to mypage story detail
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/story/sharing-id/{sharingId}")
	public String redirectMypageStory(@PathVariable Long sharingId, HttpServletRequest request, Principal principal) {
		Story adoptedStory = storyService.getAdoptedStory(sharingId);
		String email = principal.getName();
		
		if (adoptedStory.getMember().getEmail().equals(email))
			return "redirect:/mypage/story/detail/" + adoptedStory.getId();
		else 
			return "redirect:" + request.getHeader("Referer");
			
	}
}
