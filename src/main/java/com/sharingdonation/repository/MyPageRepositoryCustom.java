package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sharingdonation.dto.MyPageEnterPricePrivacyDto;
import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.MyPagePrivacyDto;
import com.sharingdonation.dto.MyPageStoryListDto;
import com.sharingdonation.entity.Member;

public interface MyPageRepositoryCustom {
	
 MyPageMainDto	getMyPageMain(Long memberId);
 
 MyPagePrivacyDto getMyPagePrivacy(Long memberId);
 
 Long updateMyPrivacy(MyPagePrivacyDto myPagePrivacyDto, Long memberId); 
 
 MyPageEnterPricePrivacyDto getMyPageEnterPricePrivacy(Long memberId);

 Long updateMyEnterPricePrivacy(MyPageEnterPricePrivacyDto myPageEnterPricePrivacyDto, Long memberId); 
 
 Page<MyPageStoryListDto> getMyPageStoryList(Long memberId, Pageable pageable);
 
}
