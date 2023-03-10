package com.sharingdonation.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sharingdonation.dto.MemberAllDto;
 
import com.sharingdonation.dto.SearchDto;
import com.sharingdonation.entity.QMember;

import lombok.val;
 

 
 

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

	
	  private BooleanExpression nameLike(String searchQuery){
	        return StringUtils.isEmpty(searchQuery) ? QMember.member.name.like("%" +searchQuery + "%") :null  ;
	    }
	  
	  private BooleanExpression nickNameLike(String searchQuery){
	        return StringUtils.isEmpty(searchQuery) ? QMember.member.nickName.like("%" +searchQuery + "%") :null ;
	    }
	  
	   
	  
	@Override
	public Page<MemberAllDto> getAdminMemberList(SearchDto searchDto, Pageable pageable) {
		 QMember member = QMember.member;
		// val containsName = member.name.contains(searchDto.getSearchQuery());//%SearchQuery%
		// val containsNickName = member.nickName.contains(searchDto.getSearchQuery());//%SearchQuery%
		 List<MemberAllDto> content = queryFactory
				 .select(Projections.fields(MemberAllDto.class,
						 member.id,
						 member.name,
						 member.nickName,
						 member.regTime,
						 member.role))
				 .from(member)
				 //.where(member.delYn.eq("N").or(nameLike(searchDto.getSearchQuery())).or(nickNameLike(searchDto.getSearchQuery())))
				 .where(member.delYn.eq("N"))
				 .where(member.name.contains(searchDto.getSearchQuery()).or(member.nickName.contains(searchDto.getSearchQuery())))
				 .orderBy(member.id.desc())
	             .offset(pageable.getOffset())
	             .limit(pageable.getPageSize())
	             .fetch();	
				 
//		long total = queryFactory
//				.select(Wildcard.count)
//				.from(member)
//				.where(member.delYn.eq("N"))
//				.fetchOne();
		
		long total = queryFactory
				.select(Projections.fields(MemberAllDto.class,
						 member.id,
						 member.name,
						 member.nickName,
						 member.regTime,
						 member.role))
				 .from(member)
				 //.where(member.delYn.eq("N").or(nameLike(searchDto.getSearchQuery())).or(nickNameLike(searchDto.getSearchQuery())))
				 .where(member.delYn.eq("N"))
				 .where(member.name.contains(searchDto.getSearchQuery()).or(member.nickName.contains(searchDto.getSearchQuery())))
				 .orderBy(member.id.desc())
	             .offset(pageable.getOffset())
	             .limit(pageable.getPageSize())
	             .fetchCount();

				 return new PageImpl<>(content, pageable, total);
	}
	
	

	
	
	
}
