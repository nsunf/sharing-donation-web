package com.sharingdonation.repository;

import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.MyPagePrivacyDto;

public interface MyPageRepositoryCustom {
	
 MyPageMainDto	getMyPageMain(Long memberId);
 
 MyPagePrivacyDto getMyPagePrivacy(Long memberId);

}
