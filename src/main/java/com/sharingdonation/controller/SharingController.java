package com.sharingdonation.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.service.AreaService;
import com.sharingdonation.service.CategoryService;
import com.sharingdonation.service.MyPageService;
import com.sharingdonation.service.SharingHeartService;
import com.sharingdonation.service.SharingImgService;
import com.sharingdonation.service.SharingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sharing")
@RequiredArgsConstructor
public class SharingController {
	
	private final SharingService sharingService;
	private final AreaService areaService;
	private final CategoryService categoryService;
	private final SharingImgService sharingImgService;
	private final SharingHeartService sharingHeartService;
	private final MemberRepository memberRepo;
	private final MyPageService myPageService;
	
	private Member getTmpMember(Role role) {
		List<Member> memberList = memberRepo.findAll().stream().filter(m -> m.getRole() == role).toList();
		if (memberList.size() > 0)
			return memberList.get(0);
		else 
			return null;
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

	@GetMapping("/area/{areaName}")
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
	
	@GetMapping("/{id}")
	public String getSharingDto(@PathVariable Long id, Model model) {

		model.addAttribute("sharingDto", sharingService.getSharingDto(id));
		model.addAttribute("sharingImgDtoList", sharingImgService.getSharingImgDtoList(id));
		model.addAttribute("sharingHeartDto", sharingHeartService.getSharingHeartDto(id));
		model.addAttribute("sharingHeartCount", sharingHeartService.getSharingHeartCount(id));

		return "sharing/sharingDetail";
	}
	
	@GetMapping("/new")
	public String createSharingForm(Model model) {
		model.addAttribute("title", "나눔 상품 등록");
		model.addAttribute("sharingFormDto", new SharingFormDto());
		model.addAttribute("areaDtoList", areaService.getAreaList());
		model.addAttribute("categoryDtoList", categoryService.getCategoryDtoLIst());
		return "sharing/editSharing";
	}
	
	@PostMapping("/new")
	public String createSharing(@Valid SharingFormDto sharingFormDto, BindingResult bindingResult, List<MultipartFile> sharingImgFileList, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("areaDtoList", areaService.getAreaList());
			model.addAttribute("categoryDtoList", categoryService.getCategoryDtoLIst());
			return "sharing/editSharing";
		}
		
		try {
			if (sharingFormDto.getId() == null)
				sharingService.saveSharing(sharingFormDto, sharingImgFileList);
			else 
				sharingService.updateSharing(sharingFormDto, sharingImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/sharing/";
	}
	
	@GetMapping("/edit/{id}")
	public String editSharing(@PathVariable Long id, Model model) {
		model.addAttribute("title", "나눔 상품 신청 관리");
		model.addAttribute("sharingFormDto", sharingService.getSharingFormDto(id));
		model.addAttribute("areaDtoList", areaService.getAreaList());
		model.addAttribute("categoryDtoList", categoryService.getCategoryDtoLIst());
		model.addAttribute("sharingImgDtoList", sharingImgService.getSharingImgDtoList(id));
		return "sharing/editSharing";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteSharing(@PathVariable Long id) {
		sharingService.deleteSharing(id);
		return "redirect:/sharing/mypage";
	}
	
	
	// MypageController로 이동 필요
	@GetMapping(value = {"/mypage", "/mypage/{page}"})
	public String mypageSharingList(@PathVariable("page") Optional<Integer> page , Model model) {
		// 임시 멤버
		Member tmpMemeber = getTmpMember(Role.USER);
		MyPageMainDto myPageDto = myPageService.getMyPageMain(tmpMemeber.getId());

		Pageable pageable = PageRequest.of(page.orElse(0), 6);
		
		Page<SharingDto> sharingDtoList = sharingService.getSharingDtoList(tmpMemeber.getId(), pageable);

		model.addAttribute("mypage", myPageDto);
		model.addAttribute("sharingDtoList", sharingDtoList);
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);
		
		return "mypage/registeredShareList";
	}

	@GetMapping(value = {"/mypage/shared", "/mypage/shared/{page}"})
	public String mypageAdoptedSharingList(@PathVariable("page") Optional<Integer> page , Model model) {
		Member tmpMember = getTmpMember(Role.USER);
		Pageable pageable = PageRequest.of(page.orElse(0), 6);
		
		Page<SharingDto> sharingDtoList = sharingService.getAdoptedSharingDtoList(tmpMember.getId(), pageable);

		model.addAttribute("sharingDtoList", sharingDtoList);
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);
		
		return "mypage/adoptedShareList";
	}
	
	
	// AdminController로 이동 필요
	@GetMapping("/admin")
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

	@PostMapping("/admin/approve")
	public String adminSharingApprove(@RequestParam Long id, @RequestParam int point) {
		sharingService.approveSharing(id, point);
		return "redirect:/sharing/admin";
	}
	
	@PostMapping("/admin/delete")
	public @ResponseBody ResponseEntity<?> adminSharingDelete(@RequestBody Map<String, Object> map) {
		List<String> tmp = (List<String>)map.get("sharingIdList");
		sharingService.deleteSharings(tmp.stream().map(s -> Long.valueOf(s)).toList());
		return new ResponseEntity<String>("approve", HttpStatus.OK);
	}
	
	@GetMapping("/admin/edit/{id}")
	public String adminEditSharing(@PathVariable Long id, Model model) {
		model.addAttribute("title", "나눔 상품 승인 관리");
		model.addAttribute("sharingFormDto", sharingService.getSharingFormDto(id));
		model.addAttribute("areaDtoList", areaService.getAreaList());
		model.addAttribute("categoryDtoList", categoryService.getCategoryDtoLIst());
		model.addAttribute("sharingImgDtoList", sharingImgService.getSharingImgDtoList(id));

		return "sharing/editSharing";
	}
	
	@GetMapping("/heart/{id}")
	public @ResponseBody ResponseEntity<?> toggleHeart(@PathVariable Long id) {
		sharingHeartService.toggleSharingHeart(id);
		Long heartCount = sharingHeartService.getSharingHeartCount(id);
		return new ResponseEntity<Long>(heartCount, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String test() {
		return "dist/longsiru/donated-board-create";
	}
}
