package com.sharingdonation.controller;

import java.security.Principal;
import java.util.List;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sharingdonation.dto.SharingBoardCommentDto;
import com.sharingdonation.dto.SharingBoardDto;
import com.sharingdonation.dto.SharingBoardFormDto;
import com.sharingdonation.dto.SharingDto;
import com.sharingdonation.entity.SharingBoardComment;
import com.sharingdonation.service.SharingBoardService;
import com.sharingdonation.service.SharingHeartService;
import com.sharingdonation.service.SharingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sharing_board")
public class SharingBoardController {

	private final SharingBoardService sharingBoardService;
	private final SharingService sharingService;
	private final SharingHeartService sharingHeartService;

	// 게시판 화면 띄워줌
	@GetMapping(value = { "", "{page}" })
	public String sharingBoard(@PathVariable("page") Optional<Integer> page, @RequestParam Optional<String> searchName,
			Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<SharingBoardDto> sharingBoardPage = sharingBoardService
				.getCompletePostPage(searchName.isPresent() ? searchName.get() : "", pageable);
		model.addAttribute("maxPage", 5);
		model.addAttribute("sharingBoardList", sharingBoardPage);
		return "sharing/sharedBoard";
	}

	// 좋아요 체크
	@GetMapping(value = "/heart/{id}")
	public @ResponseBody ResponseEntity<?> toggleSharedHeart(@PathVariable Long id) {
		sharingHeartService.toggleSharingBoardHeart(id);
		Long boardHeartCount = sharingHeartService.getSharingBoardHeartCount(id);
		return new ResponseEntity<Long>(boardHeartCount, HttpStatus.OK);
	}

	// 게시글 보기, 댓글 보여줌
	@GetMapping(value = "/view/{shared_post_id}")
	public String ViewSharedPost(Model model, @PathVariable("shared_post_id") Long id) {
		SharingBoardDto sharingBoardDto = sharingBoardService.getCompletePost(id);
		List<SharingBoardCommentDto> sharingBoardCommentDtoList = sharingBoardService.getBoardCommentList(id);
		model.addAttribute("sharingBoardDto", sharingBoardDto);
		model.addAttribute("sharingBoardCommentDtoList", sharingBoardCommentDtoList);

		return "sharing/sharedDetail";
	}

	// 게시글 수정
	@PostMapping(value = "/view/{shared_post_id}")
	public String updateSharingBoard(@Valid SharingBoardFormDto sharingBoardFormDto, BindingResult bindingResult, Model model) {

	}

	// 댓글 등록
	@PostMapping(value = "/view/{shared_post_id}/comment")
	public String insertComment(@PathVariable("shared_post_id") Long id, @RequestParam String comment, Model model) {
		// 가짜 데이터 member_id 넣었음
		sharingBoardService.insertComment(1L, id, comment);

		return "redirect:/sharing_board/view/" + id;
	}

	// 댓글 삭제
	@DeleteMapping(value = "/view/{shared_post_id}/comment/delete")
	public @ResponseBody ResponseEntity deleteComment(@PathVariable("shared_post_id") Long comment_id) {
		SharingBoardComment sharingBoardComment = sharingBoardService.getSharingBoardComment(comment_id);
		/*
		 * if(!sharingBoardService.validateComment(comment_id)) { return new
		 * ResponseEntity<String>("댓글 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN); }
		 */
		sharingBoardService.deleteComment(comment_id);

		return new ResponseEntity<Long>(comment_id, HttpStatus.OK);
	}

	// 인증완료 게시글 작성 페이지 보여줌
	@GetMapping(value = "admin/edit/{shared_id}")
	public String getinsertSharedBoardPost(Model model, @PathVariable("shared_id") Long id) {
		SharingDto sharingDto = sharingService.getSharingDto(id);
		SharingBoardFormDto sharingBoardFormDto = new SharingBoardFormDto();
		sharingBoardFormDto.setSharing_id(id);
		model.addAttribute("sharingDto", sharingDto);
		model.addAttribute("sharingBoardFormDto", sharingBoardFormDto);
		return "admin/editSharedBoard";
	}

	// 인증완료 게시글 작성
	@PostMapping(value = "admin/edit/{shared_id}")
	public String insertSharedBoardPost(@PathVariable("shared_id") Long id,
			@Valid SharingBoardFormDto sharingBoardFormDto, BindingResult bindingResult, Model model,
			List<MultipartFile> sharingBoardImgFileList) {

		SharingDto sharingDto = sharingService.getSharingDto(id);
		model.addAttribute("sharingDto", sharingDto);

		if (bindingResult.hasErrors()) {
			return "admin/editSharedBoard";
		}
		if (sharingBoardImgFileList.isEmpty() && sharingBoardFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
			return "admin/editSharedBoard";
		}

		try {
			sharingBoardService.insertSharedBoardPost(sharingBoardFormDto, sharingBoardImgFileList);
			model.addAttribute("errorMessage", "게시글이 등록되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "게시글이 정상적으로 등록되지 않았습니다.");
			return "admin/editSharedBoard";
		}
		return "redirect:/sharing_board";
	}

	///////////////////////////////////////////////
	// admin 나눔완료 게시글 관리 게시판 페이지 보여줌
	@GetMapping(value = "admin/sharedList")
	public String adminSharedPostBoard() {
		return "admin/sharedList";
	}
	///////////////////////////////////////////////////
}
