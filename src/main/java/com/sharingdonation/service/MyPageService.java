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
	public MyPagePrivacyDto getMyPagePrivacy(Principal principal) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.getMyPagePrivacy(member.getId());
	}
	
	
	@Transactional
	public Long myPrivacyUpdate(MyPagePrivacyDto myPagePrivacyDto,Principal principal) {

		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.updateMyPrivacy(myPagePrivacyDto, member.getId());
 
	}
	
	@Transactional(readOnly = true)
	public MyPageEnterPricePrivacyDto getMyPageEnterPricePrivacyDto(Principal principal) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.getMyPageEnterPricePrivacy(member.getId());
	}
	
	@Transactional
	public Long myEnterpricePrivacyUpdate(MyPageEnterPricePrivacyDto myPageEnterPricePrivacyDto,Principal principal) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.updateMyEnterPricePrivacy(myPageEnterPricePrivacyDto,member.getId());
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
		System.out.println(member.getId());
		return myPageRepository.getMyPageStoryDetail(member.getId(),storyId);
		
	}
	
	@Transactional
	public Long myPageStoryDetailUpdate(MyPageStoryDetailDto myPageStoryDetailDto, Principal principal, Long storyId) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.updateMyPageStoryDetail(myPageStoryDetailDto, member.getId(), storyId);
	}
	
	@Transactional
	public Long mypageStoryDetailDelete(Principal principal, Long storyId) {
		String email = principal.getName();
		Member member = memberRepository.findByEmail(email);
		return myPageRepository.deleteMyPageStoryDetail(member.getId(), storyId);
	}
	
	
	
	
	
}
