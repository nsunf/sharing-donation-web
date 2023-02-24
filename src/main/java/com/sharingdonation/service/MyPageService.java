package com.sharingdonation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.MyPagePrivacyDto;
import com.sharingdonation.repository.MyPageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

		private final MyPageRepository myPageRepository ;
	 
	@Transactional(readOnly = true)
	public MyPageMainDto getMyPageMain(Long memberId) {
		
		return myPageRepository.getMyPageMain(memberId);
	}
	
	@Transactional
	public MyPagePrivacyDto getMyPagePrivacy(Long memberId) {
		return myPageRepository.getMyPagePrivacy(memberId);
	}
	
	
	
	
}
