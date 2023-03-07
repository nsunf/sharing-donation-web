package com.sharingdonation.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.constant.Role;
import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.SharingDto;
import com.sharingdonation.dto.SharingFormDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.Sharing;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.SharingBoardRepository;
import com.sharingdonation.service.AreaService;
import com.sharingdonation.service.CategoryService;
import com.sharingdonation.service.MyPageService;
import com.sharingdonation.service.SharingHeartService;
import com.sharingdonation.service.SharingImgService;
import com.sharingdonation.service.SharingService;
import com.sharingdonation.service.StoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SharingController {
	
	private final SharingService sharingService;
	private final AreaService areaService;
	private final CategoryService categoryService;
	private final SharingImgService sharingImgService;
	private final SharingHeartService sharingHeartService;
	private final StoryService storyService;
	private final MemberRepository memberRepo;
	private final MyPageService myPageService;
	private final SharingBoardRepository sharingBoardRepository;
	
	private static Member user = null;
	private static Member com = null;
	
	private Member getTmpMember(Role role) {
		Member member = null;
		List<Member> memberList = memberRepo.findAll().stream().filter(m -> m.getRole() == role).toList();
		if (memberList.size() > 0) member = memberList.get(0);

		switch (role) {
		case USER:
			if (user == null) user = member;
			return user;
		case COM:
			if (com == null) com = member;
			return com;
		case ADMIN:
			break;
		}
		
		return member;
	}
	
//	@GetMapping("")
//	public String sharingList(@RequestParam Optional<Integer> page, @RequestParam Optional<String> search, Model model) {
//		Pageable pageable = PageRequest.of(page.orElse(0), 9);
//
//		model.addAttribute("sharingDtoList", sharingService.getSharingDtoList(search.orElse(null), pageable));
//		model.addAttribute("page", pageable.getPageNumber());
//		model.addAttribute("maxPage", 5);
//
//		return "sharing/sharingList";
//	}

	// 지역별 나눔 목록 리스트
	@GetMapping("sharing/area/{areaName}")
	public String sharingListByArea(@PathVariable String areaName, @RequestParam Optional<Integer> page, @RequestParam Optional<String> search, @RequestParam Optional<String> cat, Model model) {
		Pageable pageable = PageRequest.of(page.orElse(0), 9);
		String _cat = cat.orElse(null);
		
		String catName = (_cat != null && _cat.equals("전체")) ? null : cat.orElse(null);
		
		model.addAttribute("sharingDtoList", sharingService.getSharingDtoList(search.orElse(null), areaName, catName, pageable));
		model.addAttribute("area", areaName);
		model.addAttribute("search", search.orElse(null));
		model.addAttribute("cat", cat.orElse(null));
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);

		return "sharing/sharingList";
	}
	
	// 나눔 상세
	@GetMapping("sharing/{id}")
	public String getSharingDto(@PathVariable Long id, Principal principal, Model model) {
//		Member member = getTmpMember(Role.USER);
		String email = principal.getName();
		Member member = memberRepo.findByEmail(email);

		SharingDto sharingDto = sharingService.getSharingDto(id);
		
		model.addAttribute("storyFormDto", storyService.getStoryFormDto(id, member.getId()));
		model.addAttribute("sharingDto", sharingDto);
		model.addAttribute("sharingImgDtoList", sharingImgService.getSharingImgDtoList(id));
		model.addAttribute("sharingHeartDto", sharingHeartService.getSharingHeartDto(member.getId(), id));
		model.addAttribute("sharingHeartCount", sharingHeartService.getSharingHeartCount(id));

		return "sharing/sharingDetail";
	}
	
	// 나눔 등록 페이지
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("sharing/new")
	public String createSharingForm(Model model) {
		model.addAttribute("title", "나눔 상품 등록");
		model.addAttribute("sharingFormDto", new SharingFormDto());
		model.addAttribute("areaDtoList", areaService.getAreaList());
		model.addAttribute("categoryDtoList", categoryService.getCategoryDtoLIst());
		return "sharing/editSharing";
	}
	
	// 나눔 등록
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("sharing/new")
	public String createSharing(@Valid SharingFormDto sharingFormDto, BindingResult bindingResult, List<MultipartFile> sharingImgFileList, Principal principal, Model model) {
//		Member member = getTmpMember(Role.USER);
		String email = principal.getName();
		Member member = memberRepo.findByEmail(email);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "나눔 상품 등록");
			model.addAttribute("areaDtoList", areaService.getAreaList());
			model.addAttribute("categoryDtoList", categoryService.getCategoryDtoLIst());
			return "sharing/editSharing";
		}
		
		Sharing sharing = null;
		
		try {
			if (sharingFormDto.getId() == null)
				sharing = sharingService.saveSharing(sharingFormDto, member.getId(), sharingImgFileList);
			else 
				sharing = sharingService.updateSharing(sharingFormDto, sharingImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (sharing != null) {
			if (sharing.getConfirmYn().equals("Y")) {
				return "redirect:/sharing/" + sharing.getId();
			} else {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
					return "redirect:/mypage/sharing";
				} else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
					return "redirect:/admin/sharing";
				}
			}
		}
			
		return "redirect:/sharing/";
	}
	
	// 나눔 수정 페이지
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("sharing/edit/{id}")
	public String editSharing(@PathVariable Long id, Principal principal, Model model) {
		String email = principal.getName();
		Member member = memberRepo.findByEmail(email);

		model.addAttribute("title", "나눔 상품 내역");
		model.addAttribute("member", member);
		model.addAttribute("sharingFormDto", sharingService.getSharingFormDto(id));
		model.addAttribute("areaDtoList", areaService.getAreaList());
		model.addAttribute("categoryDtoList", categoryService.getCategoryDtoLIst());
		model.addAttribute("sharingImgDtoList", sharingImgService.getSharingImgDtoList(id));
		return "sharing/editSharing";
	}
	
	// 나눔 삭제
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("sharing/delete/{id}")
	public String deleteSharing(@PathVariable Long id) {
		sharingService.deleteSharing(id);
		return "redirect:/mypage/sharing";
	}
	
	
	// MypageController로 이동 필요
	// 마이페이지 나눔 등록 내역
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = {"mypage/sharing", "mypage/sharing/{page}"})
	public String mypageSharingList(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
		// 임시 멤버
//		Member member = getTmpMember(Role.USER);
		MyPageMainDto myPageDto = myPageService.getMyPageMain(principal);

		Pageable pageable = PageRequest.of(page.orElse(0), 6);
		
		Page<SharingDto> sharingDtoList = sharingService.getSharingDtoListById(principal, pageable);

		model.addAttribute("mypage", myPageDto);
		model.addAttribute("sharingDtoList", sharingDtoList);
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);
		
		return "mypage/registeredShareList";
	}

	// 마이페이지 나눔 받은 내역
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = {"mypage/shared", "mypage/shared/{page}"})
	public String mypageAdoptedSharingList(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
//		Member member = getTmpMember(Role.USER);
//		Member member = memberRepo.findById(10L).orElseThrow(EntityNotFoundException::new);
		
		MyPageMainDto myPageDto = myPageService.getMyPageMain(principal);
		Pageable pageable = PageRequest.of(page.orElse(0), 6);
		
		Page<SharingDto> sharingDtoList = sharingService.getAdoptedSharingDtoListById(principal, pageable);

		model.addAttribute("mypage", myPageDto);
		model.addAttribute("sharingDtoList", sharingDtoList);
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);
		
		return "mypage/adoptedShareList";
	}
	
	
	// AdminController로 이동 필요
	// 관리자 나눔 리스트
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin/sharing")
	public String adminSharing(@RequestParam Optional<Integer> page, @RequestParam Optional<String> filter, @RequestParam Optional<String> search, Model model) {
		Pageable pageable = PageRequest.of(page.orElse(0), 10);
		
		model.addAttribute("sharingDtoList", sharingService.getAdminSharingDtoList(pageable, filter.orElse(null), search.orElse(null)));
		model.addAttribute("filter", filter.orElse("title"));
		model.addAttribute("search", search.orElse(""));
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);

		return "admin/sharingList";
	}
	
//	@PostMapping("/admin/approve")
//	public @ResponseBody ResponseEntity<?> adminSharingApprove(@RequestBody Map<String, Object> map) {
//		System.out.println(map.get("sharingIdList"));
//		List<String> tmp = (List<String>)map.get("sharingIdList");
//		sharingService.approveSharings(tmp.stream().map(s -> Long.valueOf(s)).toList());
//		return new ResponseEntity<String>("approve", HttpStatus.OK);
//	}
	
	// 관리자 나눔 상품 승인
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/admin/sharing/approve")
	public String adminSharingApprove(@RequestParam Long id, @RequestParam int point) {
		sharingService.approveSharing(id, point);
		return "redirect:/admin/sharing";
	}
	
	// 나눔 상품 삭제
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/admin/sharing/delete")
	public @ResponseBody ResponseEntity<?> adminSharingDelete(@RequestBody Map<String, Object> map) {
		List<String> tmp = (List<String>)map.get("sharingIdList");
		sharingService.deleteSharings(tmp.stream().map(s -> Long.valueOf(s)).toList());
		return new ResponseEntity<String>("approve", HttpStatus.OK);
	}
	
	
	// 관리자 나눔 상품 수정 페이지
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin/sharing/edit/{id}")
	public String adminEditSharing(@PathVariable Long id, Principal principal, Model model) {
		String email = principal.getName();
		Member member = memberRepo.findByEmail(email);

		model.addAttribute("title", "나눔 상품 승인 관리");
		model.addAttribute("member", member);
		model.addAttribute("sharingFormDto", sharingService.getSharingFormDto(id));
		model.addAttribute("areaDtoList", areaService.getAreaList());
		model.addAttribute("categoryDtoList", categoryService.getCategoryDtoLIst());
		model.addAttribute("sharingImgDtoList", sharingImgService.getSharingImgDtoList(id));
		model.addAttribute("sharingBoard",sharingBoardRepository.findBySharingId(id));

		return "sharing/editSharing";
	}
	
	// 나눔 좋아요
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("sharing/heart/{id}")
	public @ResponseBody ResponseEntity<?> toggleHeart(@PathVariable Long id, Principal principal) {
//		Member member = getTmpMember(Role.USER);
		String email = principal.getName();
		Member member = memberRepo.findByEmail(email);
		sharingHeartService.toggleSharingHeart(member.getId(), id);
		Long heartCount = sharingHeartService.getSharingHeartCount(id);
		return new ResponseEntity<Long>(heartCount, HttpStatus.OK);
	}
}
