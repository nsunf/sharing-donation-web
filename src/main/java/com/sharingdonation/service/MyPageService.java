package com.sharingdonation.service;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharingdonation.dto.MyPageEnterPricePrivacyDto;
import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.MyPagePrivacyDto;
import com.sharingdonation.dto.MyPageStoryDetailDto;
import com.sharingdonation.dto.MyPageStoryListDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.repository.MemberRepository;
import com.sharingdonation.repository.MyPageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

		private final MyPageRepository myPageRepository ;
		private final MemberRepository memberRepository ;
	 
	@Transactional(readOnly = true)
	public MyPageMainDto getMyPageMain(Principal principal) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.getMyPageMain(member.getId());
	}
	
	@Transactional(readOnly = true)
	public MyPagePrivacyDto getMyPagePrivacy(Long memberId) {
		return myPageRepository.getMyPagePrivacy(memberId);
	}
	
	
	@Transactional
	public Long myPrivacyUpdate(MyPagePrivacyDto myPagePrivacyDto,Long memberId) {

		return myPageRepository.updateMyPrivacy(myPagePrivacyDto,memberId);
	}
	
	@Transactional(readOnly = true)
	public MyPageEnterPricePrivacyDto getMyPageEnterPricePrivacyDto(Long memberId) {
		return myPageRepository.getMyPageEnterPricePrivacy(memberId);
	}
	
	@Transactional
	public Long myEnterpricePrivacyUpdate(MyPageEnterPricePrivacyDto myPageEnterPricePrivacyDto,Long memberId) {

		return myPageRepository.updateMyEnterPricePrivacy(myPageEnterPricePrivacyDto,memberId);
	}
	
	@Transactional(readOnly = true)
	public Page<MyPageStoryListDto> getMyPageStoryList(Principal principal, Pageable pageable){
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.getMyPageStoryList(member.getId(), pageable);
		
	}
	
	@Transactional(readOnly = true)
	public MyPageStoryDetailDto getMyPageStoryDetail(Principal principal, Long storyId){
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.getMyPageStoryDetail(member.getId(),storyId);
		
	}
	
	@Transactional
	public Long myPageStoryDetailUpdate(MyPageStoryDetailDto myPageStoryDetailDto, Principal principal, Long storyId) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.updateMyPageStoryDetail(myPageStoryDetailDto, member.getId(), storyId);
	}
	
	
	
	
	
}
