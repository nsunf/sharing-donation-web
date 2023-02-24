package com.sharingdonation.repository;

import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.MyPagePrivacyDto;
import com.sharingdonation.entity.Member;

public interface MyPageRepositoryCustom {
	
 MyPageMainDto	getMyPageMain(Long memberId);
 
 MyPagePrivacyDto getMyPagePrivacy(Long memberId);
 
 Long updateMyPrivacy(MyPagePrivacyDto myPagePrivacyDto, Long memberId); 

}
