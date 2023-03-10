package com.sharingdonation.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.validator.internal.util.privilegedactions.IsClassPresent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.SharingBoardCommentDto;
import com.sharingdonation.dto.SharingBoardDto;
import com.sharingdonation.dto.SharingBoardFormDto;
import com.sharingdonation.dto.SharingBoardImgDto;
import com.sharingdonation.dto.SharingDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.SharingBoardComment;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.service.SharingBoardImgService;
import com.sharingdonation.service.SharingBoardService;
import com.sharingdonation.service.SharingHeartService;
import com.sharingdonation.service.SharingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/sharing_board")
public class SharingBoardController {

	private final SharingBoardService sharingBoardService;
	private final SharingService sharingService;
	private final SharingHeartService sharingHeartService;
	private final SharingBoardImgService sharingBoardImgService;
	private final MemberRepository memberRepository;

	// 게시판 화면 띄워줌
	@GetMapping(value = { "/sharing_board", "/sharing_board/{page}" })
	public String sharingBoard(@PathVariable("page") Optional<Integer> page, @RequestParam Optional<String> searchName,
			Long id ,Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<SharingBoardDto> sharingBoardPage = sharingBoardService
				.getCompletePostPage(searchName.isPresent() ? searchName.get() : "", pageable);
		model.addAttribute("maxPage", 5);
		model.addAttribute("sharingBoardList", sharingBoardPage);
		model.addAttribute("sharedHeartCount",sharingHeartService.getSharingBoardHeartCount(id));
		return "sharing/sharedBoard";
	}

	// 좋아요 체크
	@GetMapping(value = "/sharing_board/heart/{id}")
	public @ResponseBody ResponseEntity<?> toggleSharedHeart(@PathVariable Long id, Principal principal) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		sharingHeartService.toggleSharingBoardHeart(member.getId(), id);
		Long boardHeartCount = sharingHeartService.getSharingBoardHeartCount(id);
		return new ResponseEntity<Long>(boardHeartCount, HttpStatus.OK);
	}

	// 게시글 보기, 댓글 보여줌
	@GetMapping(value = "/sharing_board/view/{shared_post_id}")
	public String viewSharedPost(Model model, @PathVariable("shared_post_id") Long id, Principal principal) {

		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		
		SharingBoardDto sharingBoardDto = sharingBoardService.getCompletePost(id);
		List<SharingBoardImgDto> sharingBoardImgDtoList = sharingBoardImgService.getSharingBoardImgs(id);
		List<SharingBoardCommentDto> sharingBoardCommentDtoList = sharingBoardService.getBoardCommentList(id);

		model.addAttribute("sharingBoardHeartDto",sharingHeartService.getSharingBoardHeartDto(member.getId(), id));
		model.addAttribute("sharingBoardDto", sharingBoardDto);
		model.addAttribute("sharingBoardImgDtoList", sharingBoardImgDtoList);
		model.addAttribute("sharingBoardCommentDtoList", sharingBoardCommentDtoList);

		return "sharing/sharedDetail";
	}

	// 게시글 수정 페이지 보여줌
	@GetMapping(value = "/sharing_board/update/{sharedBoard_id}")
	public String sharingBoardDetail(@PathVariable("sharedBoard_id") Long id, Model model) {
		SharingBoardFormDto sharingBoardFormDto = sharingBoardService.viewsharingBoardFormDto(id);
		model.addAttribute("sharingBoardFormDto", sharingBoardFormDto);

		return "admin/updateSharedBoard";
	}

	// 게시글 수정
	@PostMapping(value = "/sharing_board/update/{sharedBoard_id}")
	public String updateSharedBoard(@Valid SharingBoardFormDto sharingBoardFormDto, BindingResult bindingResult,
			Model model, List<MultipartFile> sharingBoardImgFileList) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("sharingBoardFormDto", sharingBoardFormDto);
			return "admin/updateSharedBoard";
		}

		if (sharingBoardImgFileList.get(0).isEmpty() && sharingBoardFormDto.getId() == null) {
			model.addAttribute("errorMessage", "이미지를 찾을 수 없습니다.");
			return "admin/updateSharedBoard";
		}

		try {
			sharingBoardService.sharingBoardUpdate(sharingBoardFormDto, sharingBoardImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "게시글 수정이 정상적으로 이루어지지 않았습니다.");
			return "admin/updateSharedBoard";
		}
		return "redirect:/sharing_board/view/" + sharingBoardFormDto.getId();
	}

	// 게시글 삭제
	@DeleteMapping(value = "/sharing_board/view/{shared_post_id}/delete")
	public @ResponseBody ResponseEntity deleteSharingBoard(@PathVariable("shared_post_id") Long sharingBoardId) {
		try {
			sharingBoardImgService.deleteImgsBySharingBoardId(sharingBoardId);
			sharingHeartService.deleteSharingBoardHeart(sharingBoardId);
			sharingBoardService.deleteSharingBoard(sharingBoardId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Long>(sharingBoardId, HttpStatus.OK);
	}
	//관리자 페이지 게시글 삭제
	@DeleteMapping(value="/admin/sharedList/{shared_post_id}/delete")
	public @ResponseBody ResponseEntity deleteAdminSharingBoard(@PathVariable("shared_post_id") Long sharingBoardId) {
		try {
			sharingBoardImgService.deleteImgsBySharingBoardId(sharingBoardId);
			sharingHeartService.deleteSharingBoardHeart(sharingBoardId);
			sharingBoardService.deleteSharingBoard(sharingBoardId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Long>(sharingBoardId, HttpStatus.OK);
	}
	// 댓글 등록
	@PostMapping(value = "/sharing_board/view/{shared_post_id}/comment")
	public String insertComment(@PathVariable("shared_post_id") Long id, @RequestParam String comment, Model model,
			Principal principal) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		Long member_id = member.getId();
		sharingBoardService.insertComment(member_id, id, comment);

		return "redirect:/sharing_board/view/" + id;
	}

	// 댓글 삭제
	@DeleteMapping(value = "/sharing_board/view/{shared_post_id}/comment/delete")
	public @ResponseBody ResponseEntity deleteComment(@PathVariable("shared_post_id") Long comment_id,
			Principal principal) {
		SharingBoardComment sharingBoardComment = sharingBoardService.getSharingBoardComment(comment_id);
		if (!sharingBoardService.validateComment(comment_id, principal.getName())) {
			return new ResponseEntity<String>("댓글 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		sharingBoardService.deleteComment(comment_id);
		return new ResponseEntity<Long>(comment_id, HttpStatus.OK);
	}

	// 인증완료 게시글 작성 페이지 보여줌
	@GetMapping(value = "admin/sharing_board/create/{shared_id}")
	public String getinsertSharedBoardPost(Model model, @PathVariable("shared_id") Long id) {
		SharingDto sharingDto = sharingService.getSharingDto(id);
		SharingBoardFormDto sharingBoardFormDto = new SharingBoardFormDto();
		sharingBoardFormDto.setSharing_id(id);
		model.addAttribute("sharingDto", sharingDto);
		model.addAttribute("sharingBoardFormDto", sharingBoardFormDto);
		return "admin/createSharedBoard";
	}

	// 인증완료 게시글 작성
	@PostMapping(value = "admin/sharing_board/create/{shared_id}")
	public String insertSharedBoardPost(@PathVariable("shared_id") Long id,
			@Valid SharingBoardFormDto sharingBoardFormDto, BindingResult bindingResult, Model model,
			List<MultipartFile> sharingBoardImgFileList, Principal principal) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		
		SharingDto sharingDto = sharingService.getSharingDto(id);
		model.addAttribute("sharingDto", sharingDto);

		if (bindingResult.hasErrors()) {
			return "admin/createSharedBoard";
		}
		if (sharingBoardImgFileList.get(0).isEmpty() && sharingBoardFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
			return "admin/createSharedBoard";
		}

		try {
			sharingBoardService.insertSharedBoardPost(sharingBoardFormDto, sharingBoardImgFileList);
			model.addAttribute("errorMessage", "게시글이 등록되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "게시글이 정상적으로 등록되지 않았습니다.");
			return "admin/createSharedBoard";
		}
		return "redirect:/admin/sharing";
	}

	///////////////////////////////////////////////////
	// admin 나눔완료 게시글 관리 게시판 페이지 보여줌
	@GetMapping(value = { "admin/sharedList", "admin/sharedList/{page}" })
	public String adminSharedPostBoard(@PathVariable("page") Optional<Integer> page,
			@RequestParam Optional<String> searchName, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<SharingBoardDto> sharingBoardPages = sharingBoardService
				.getCompletePostPage(searchName.isPresent() ? searchName.get() : "", pageable);
		
		model.addAttribute("maxPage", 5);
		model.addAttribute("sharingBoardPageList", sharingBoardPages);
		//model.addAttribute("sharingBoradDtos",sharingBoardDto);
		return "admin/sharedList";
	}

	// admin 나눔완료 게시글 관리 게시판 데이터 보여줌
	@GetMapping(value = "admin/sharedList/view/{sharedPostId}")
	public String adminViewSharedPost(Model model, @PathVariable("sharedPostId") Long id) {
		SharingBoardDto sharingBoardDto = sharingBoardService.getCompletePost(id);
		List<SharingBoardImgDto> sharingBoardImgDtoList = sharingBoardImgService.getSharingBoardImgs(id);
		List<SharingBoardCommentDto> sharingBoardCommentDtoList = sharingBoardService.getBoardCommentList(id);

		model.addAttribute("sharingBoardDto", sharingBoardDto);
		model.addAttribute("sharingBoardImgDtoList", sharingBoardImgDtoList);
		model.addAttribute("sharingBoardCommentDtoList", sharingBoardCommentDtoList);

		return "admin/sharedList";
	}
}
