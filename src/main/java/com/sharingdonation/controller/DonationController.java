package com.sharingdonation.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.validation.Valid;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.DonationAdminFormDto;
import com.sharingdonation.dto.DonationDto;
import com.sharingdonation.dto.DonationFormDto;
import com.sharingdonation.dto.SearchDto;
import com.sharingdonation.dto.SharingDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.PointRepository;
import com.sharingdonation.dto.ListDonationDto;
import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.PointDto;
import com.sharingdonation.service.DonationHeartService;
import com.sharingdonation.service.DonationService;
import com.sharingdonation.service.MyPageService;
import com.sharingdonation.service.PointService;
import com.sharingdonation.service.SharingHeartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DonationController {
	
	private final DonationService donationService;
	
	private final DonationHeartService donationHeartService;
	private final MemberRepository memberRepository;
	private final PointRepository pointRepository;
	private final PointService pointService;
	private final MyPageService myPageService;
	
	
	//list페이
	@GetMapping(value = "/donation")
	public String donationList(SearchDto donationSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get()-1 : 0, 6);
		Page<ListDonationDto> donationList = donationService.getListDonationPage(donationSearchDto, pageable);
		
//		System.out.println(donationList.getContent());
//		for(ListDonationDto ln : donationList.getContent()) {
//			System.out.println("ln.getGoalPoint():" + ln.getGoalPoint() + ", ln.getPointSum() : " + ln.getPointSum() + ", ln.getHeartCount() : " +ln.getHeartCount());
//		}
		model.addAttribute("donationList", donationList);
		model.addAttribute("donationSearchDto", donationSearchDto);
		model.addAttribute("maxPage", 5);
//		System.out.println("donation_list_check");
		return "donation/donationList";
	}
	
	@GetMapping(value="/donation/new")
	public String donationForm(Model model) {
		model.addAttribute("donationFormDto", new DonationFormDto());
		return "donation/editDonation";
	}
	
	@PostMapping(value = "/donation/new")
	public String donationNew(@Valid DonationFormDto donationFormDto, BindingResult bindingResult, Principal principal,  Model model
			, @RequestParam("donationImgFile") List<MultipartFile> donationImgFileList) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("hassErrors");
			return "donation/editDonation";
		}
		
		if(donationImgFileList.get(0).isEmpty() && donationFormDto.getId() == null) {
			System.out.println("donationImgFile");
			model.addAttribute("errorMessage", "첫버내 상품 이미지는 필수 입력 값 입니다.");
			return "donation/editDonation";
		}
		
		try {
			donationService.saveDonation(donationFormDto, donationImgFileList, principal);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "기부 등록 중 에러가 발생했습니다.");
			System.out.println("exception");
			return "donation/editDonation";
		}
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/donation/{donationId}")
	public String donationDtl(@PathVariable("donationId") Long donationId, Principal principal, Model model) {
		try {
			String email = principal.getName();
			Member member = memberRepository.findByEmail(email);
			
			DonationFormDto donationFormDto = donationService.getDonationDtl(donationId);
			model.addAttribute(donationFormDto);
			model.addAttribute("nickName", member.getNickName());
			model.addAttribute("sharingHeartDto", donationHeartService.getDonationHeartDto(member.getId(), donationId));
			model.addAttribute("sharingHeartCount", donationHeartService.getDonationHeartCount(donationId));
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			model.addAttribute("donationFormDto", new DonationFormDto());
			return "redirect:/donation";
		}
		return "donation/donationDetail";
	}
	
	
	@GetMapping(value = "/donation/edit/{donationId}")
	public String donationEditDtl(@PathVariable("donationId") Long donationId, Model model) {
		try {
			DonationFormDto donationFormDto = donationService.getDonationDtl(donationId);
			
			donationFormDto.getDonationImgDtoList();
			model.addAttribute("donationFormDto", donationFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			model.addAttribute("donationFormDto", new DonationFormDto());
			return "donation/editDonation";
		}
		return "donation/editDonation";
	}
	
	@PostMapping("/donation/edit/{id}")
	public String donationUpdate(@Valid DonationFormDto donationFormDto, BindingResult bindingResult, 
			Principal principal, Model model
//			, @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
//			, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
			, @RequestParam("donationImgFile") List<MultipartFile> donationImgFileList) {
//	donationAdminFormDto.setStartDate(startDate);
//	donationAdminFormDto.setEndDate(endDate);
	
//	DonationAdminFormDto donationAdminFormDto1 = LocalDate.parse(donationAdminFormDto1.getEndDate());
		if(bindingResult.hasErrors()) {
			return "donation/editDonation";
		}

		//첫번째 이미지가 있는지 검사(첫번째 이미지는 필수 입력값이기 때문에)
		if(donationImgFileList.get(0).isEmpty() && donationFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
			return "donation/editDonation";
		}
		
		try {
			donationService.updateDonation(donationFormDto, donationImgFileList, principal);
		} catch (Exception e) {
//			System.out.println(" controller adminDonationUpdate exception");
			e.printStackTrace();
			model.addAttribute("errorMessage", "기부 수정 중 에러가 발생하였습니다.");
			return "donation/editDonation";
		}
		
		return "redirect:/mypage/donation";
	}
	
	// 나눔 좋아요
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("donation/heart/{id}")
	public @ResponseBody ResponseEntity<?> toggleHeart(@PathVariable Long id, Principal principal) {
//			Member member = getTmpMember(Role.USER);
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		donationHeartService.toggleDonationHeart(member.getId(), id);
		Long heartCount = donationHeartService.getDonationHeartCount(id);
		return new ResponseEntity<Long>(heartCount, HttpStatus.OK);
	}
	
	// 보유포인트조회
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("pointSearch")
	public @ResponseBody ResponseEntity<?> pointSearch(Principal principal) {
		
		Long points;
		try {
			String email = principal.getName();
			Member member = memberRepository.findByEmail(email);
			points = pointRepository.pointSearch(member.getId());
		} catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
		
//		System.out.println("points ::: " + points);
		return new ResponseEntity<Long>(points, HttpStatus.OK);
	}
	
	// 포인트 사용
	@PostMapping(value = "/pointDonation")
    public @ResponseBody ResponseEntity pointDonation(
    		@RequestBody @Valid PointDto pointDto, BindingResult bindingResult
    		, Principal principal){

		Long pointId;
        try {
            pointId = donationService.pointDonation(pointDto, principal);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(pointId, HttpStatus.OK);
    }
	
	
	
	///////////////////////////////////////////////////
	//////////////////// mypage ///////////////////////
	///////////////////////////////////////////////////
	
	
	
	
	// 마이페이지 나눔 받은 내역
		@PreAuthorize("hasRole('ROLE_USER')")
		@GetMapping(value = {"mypage/donation", "mypage/donation/{page}"})
		public String mypageAdoptedSharingList(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
//			Member member = getTmpMember(Role.USER);
//			Member member = memberRepo.findById(10L).orElseThrow(EntityNotFoundException::new);
			
			MyPageMainDto myPageDto = myPageService.getMyPageMain(principal);
			Pageable pageable = PageRequest.of(page.orElse(0), 6);
			
			Page<DonationDto> donationDtoList = donationService.getDonationDtoListByMemberId(principal, pageable);
				
			model.addAttribute("mypage", myPageDto);
			model.addAttribute("donationDtoList", donationDtoList);
			model.addAttribute("page", pageable.getPageNumber());
			model.addAttribute("maxPage", 5);
			
			return "mypage/donationReqList";
//			return "mypage/registeredShareList";
		}
	
	
	
	///////////////// admin //////////////////
	
	//list페이
	@GetMapping(value = "/admin/donation")
	public String adminDonationList(SearchDto donationSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get()-1 : 0, 10);
		Page<DonationDto> donationList = donationService.getAdminListDonationPage(donationSearchDto, pageable);
		
//		System.out.println(  donationList.pa);
//		System.out.println(donationList.getContent());
//		for(DonationDto ln : donationList.getContent()) {
//			System.out.println("ln.getGoalPoint():" + ln.getId() + ", ln.getPointSum() : " + ln.getDonationName() + ":"+ ln.getStartDate());
//		}
//		System.out.println(donationList.getTotalElements() +":: " +  page.get());
		
		
//		donationList.
		
		int nowPage = (page.isPresent()) ? page.get() : 1;
		System.out.println("pages:" + nowPage);
		model.addAttribute("donationDtoList", donationList);
		model.addAttribute("donationSearchDto", donationSearchDto);
		model.addAttribute("maxPage", 5);
		model.addAttribute("rowPerPage", 10 );
		model.addAttribute("totalCount", donationList.getSize());
		model.addAttribute("pages", nowPage);
//		System.out.println("donation_list_check");
		return "admin/donationReqList";
	}
	
	@GetMapping("/admin/donation/edit/{id}")
	public String adminDonationEdit(@PathVariable Long id, Model model) {
//		DonationAdminFormDto dto = donationService.getAdminDonationDtl(id);
//		dto.getDonationImgDtoList();
//		model.addAttribute("title", "나눔 상품 승인 관리");
		model.addAttribute("donationAdminFormDto", donationService.getAdminDonationDtl(id));
//		model.addAttribute("areaDtoList", donationService.getAreaList());
//		model.addAttribute("categoryDtoList", donationService.getCategoryDtoLIst());
//		model.addAttribute("donationImgDtoList", donationService.getSharingImgDtoList(id));

//		return "../dist/longsiru/admin-donation-request-detail";
		return "admin/editDonation";
	}
	
	@PostMapping("/admin/donation/edit/{id}")
	public String adminDonationUpdate(@Valid DonationAdminFormDto donationAdminFormDto, BindingResult bindingResult, 
			Model model
//			, @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
//			, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
			, @RequestParam("donationImgFile") List<MultipartFile> donationImgFileList) {
//	System.out.println(" controller adminDonationUpdate");
//	System.out.println(donationAdminFormDto.getId());
//	System.out.println(donationAdminFormDto.getEndDate());
//	
//	donationAdminFormDto.setStartDate(startDate);
//	donationAdminFormDto.setEndDate(endDate);
	
//	DonationAdminFormDto donationAdminFormDto1 = LocalDate.parse(donationAdminFormDto1.getEndDate());
		if(bindingResult.hasErrors()) {
//			System.out.println(bindingResult.getAllErrors());
//			System.out.println(" controller adminDonationUpdate hasErrors");
			return "admin/editDonation";
		}
		
		
		//첫번째 이미지가 있는지 검사(첫번째 이미지는 필수 입력값이기 때문에)
		if(donationImgFileList.get(0).isEmpty() && donationAdminFormDto.getId() == null) {
//			System.out.println(" controller adminDonationUpdate nullcheck");
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
			return "admin/editDonation";
		}
		
		try {
			donationService.updateAdminDonation(donationAdminFormDto, donationImgFileList);
		} catch (Exception e) {
//			System.out.println(" controller adminDonationUpdate exception");
			e.printStackTrace();
			model.addAttribute("errorMessage", "기부 수정 중 에러가 발생하였습니다.");
			return "admin/editDonation";
		}
		
		return "redirect:/";
	}
}
