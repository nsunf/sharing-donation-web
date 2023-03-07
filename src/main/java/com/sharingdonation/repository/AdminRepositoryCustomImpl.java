package com.sharingdonation.repository;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sharingdonation.dto.MemberAllDto;
import com.sharingdonation.entity.QMember;
 

public class AdminRepositoryCustomImpl implements AdminRepositoryCustom{

	
private JPAQueryFactory queryFactory;

public AdminRepositoryCustomImpl(EntityManager em) {
	this.queryFactory = new JPAQueryFactory(em);
}
	
	@Override
	public Long updateAdminByMember(MemberAllDto memberAllDto, Long memberId) {
		 QMember member = QMember.member;
		 
		 long update = queryFactory
				 .update(member)
				 .set(member.role, memberAllDto.getRole())
				 .set(member.nickName,memberAllDto.getNickName())
				 .set(member.zipCode, memberAllDto.getZipCode())
				 .set(member.address,memberAllDto.getAddress())
				 .set(member.addressDetail,memberAllDto.getAddressDetail())
				 .where(member.id.eq(memberId))
				 .execute();
		return update;
	}

	@Override
	public Long deleteAdminByMember(Long memberId) {
		QMember member = QMember.member;
		
		long update = queryFactory
				.update(member)
				.set(member.delYn,"Y")
				.where(member.id.eq(memberId))
				.execute();
		return update;
	}

	@Override
	public Long updateAdminByEneterprice(MemberAllDto memberAllDto, Long memberId) {
	 
		QMember member = QMember.member;
		
		long update = queryFactory
				.update(member)
				.set(member.name,memberAllDto.getName())
				.set(member.cellphone,memberAllDto.getCellphone())
				.set(member.fax, memberAllDto.getFax())
				.set(member.zipCode, memberAllDto.getZipCode())
				.set(member.address, memberAllDto.getAddress())
				.set(member.addressDetail, memberAllDto.getAddressDetail())
				.where(member.id.eq(memberId))
				.execute();
		
		return update;
	}

	@Override
	public Long deleteAdminByEneterprice(Long memberId) {
		QMember member = QMember.member;
		
		long update = queryFactory
				.update(member)
				.set(member.delYn, "Y")
				.where(member.id.eq(memberId))
				.execute();
		
		return update;
	}
	
	

	
	
	
}
