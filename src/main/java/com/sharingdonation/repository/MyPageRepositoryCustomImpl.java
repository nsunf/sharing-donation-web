package com.sharingdonation.repository;

 

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.sharingdonation.constant.MoveType;
import com.sharingdonation.dto.MyPageEnterPricePrivacyDto;
import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.MyPagePrivacyDto;
import com.sharingdonation.dto.MyPageStoryDetailDto;
import com.sharingdonation.dto.MyPageStoryListDto;
import com.sharingdonation.dto.QMyPageMainDto;
import com.sharingdonation.entity.Member;
import com.sharingdonation.entity.QMember;
import com.sharingdonation.entity.QPoint;
import com.sharingdonation.entity.QSharing;
import com.sharingdonation.entity.QSharingImg;
import com.sharingdonation.entity.QStory;

import groovy.time.BaseDuration.From;


public class MyPageRepositoryCustomImpl implements MyPageRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public MyPageRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public MyPageMainDto getMyPageMain(Long memberId) {

		QMember member = QMember.member;
		QSharing sharing = QSharing.sharing;
		QStory story = QStory.story;
		QPoint point = QPoint.point1;
		
		
		 MyPageMainDto result = queryFactory
//				 .select(Projections.fields(MyPageMainDto.class,
//						 member.id,
//						 member.point,
//						 ExpressionUtils.as(JPAExpressions.select(sharing.id.count())
//								 .from(sharing)
//								 .where(sharing.member.id.eq(memberId).and(sharing.delYn.eq("N")))
//								 ,"share_reg"),
//						 ExpressionUtils.as(JPAExpressions.select(story.id.count())
//								 .from(story)
//								 .where(story.member.id.eq(memberId).and(story.chooseYn.eq("Y")))
//								 ,"share_take"),
//						 ExpressionUtils.as(JPAExpressions.select(point.id.count())
//								 .from(point)
//								 .where(point.member.id.eq(memberId).and(point.moveType.eq(MoveType.PLUS)))
//								 ,"share_apply"),
//						 ExpressionUtils.as(JPAExpressions.select(story.id.count())
//								 .from(story)
//								 .where(story.member.id.eq(memberId))
//								 , "share_story"),
//						 member.name,
//						 member.regTime	,	
//						 member.nickName,
//						 member.role
//						 ))
				 .select(new QMyPageMainDto(
						 member.id,
						 member.point,
						 ExpressionUtils.as(JPAExpressions.select(sharing.id.count())
								 .from(sharing)
								 .where(sharing.member.id.eq(memberId).and(sharing.delYn.eq("N")))
								 ,"share_reg"),
						 ExpressionUtils.as(JPAExpressions.select(story.id.count())
								 .from(story)
								 .where(story.member.id.eq(memberId).and(story.chooseYn.eq("Y")))
								 ,"share_take"),
						 ExpressionUtils.as(JPAExpressions.select(point.id.count())
								 .from(point)
								 .where(point.member.id.eq(memberId).and(point.moveType.eq(MoveType.PLUS)))
								 ,"share_apply"),
						 ExpressionUtils.as(JPAExpressions.select(story.id.count())
								 .from(story)
								 .where(story.member.id.eq(memberId))
								 , "share_story"),
						 member.name,
						 member.regTime	,	
						 member.nickName,
						 member.role
						 ))
				 .from(member)
				 .where(member.id.eq(memberId))
				 .fetchOne();
		 
		return result;
		
		
	}

	@Override
	public MyPagePrivacyDto getMyPagePrivacy(Long memberId) {
		
		QMember member = QMember.member;
		
		MyPagePrivacyDto result = queryFactory
				.select(Projections.fields(MyPagePrivacyDto.class,
				member.id,
				member.name,
				member.email,
				member.birth,
				member.nickName,
				member.zipCode,
				member.address,
				member.addressDetail,
				member.regTime,
				member.role))
				.from(member)
				.where(member.id.eq(memberId))
				.fetchOne();
		return result;
	}

	
	
	@Override
	public Long updateMyPrivacy(MyPagePrivacyDto myPagePrivacyDto, Long memberId) {
		QMember member2 = QMember.member;
		
		long update = queryFactory
				.update(member2)
				.set(member2.nickName, myPagePrivacyDto.getNickName())
				.set(member2.zipCode, myPagePrivacyDto.getZipCode())
				.set(member2.address, myPagePrivacyDto.getAddress())
				.set(member2.addressDetail, myPagePrivacyDto.getAddressDetail())
				.where(member2.id.eq(memberId))
				.execute();
		
		return update;
	}

	@Override
	public MyPageEnterPricePrivacyDto getMyPageEnterPricePrivacy(Long memberId) {
        QMember member = QMember.member;
		
        MyPageEnterPricePrivacyDto result = queryFactory
				.select(Projections.fields(MyPageEnterPricePrivacyDto.class,
				member.id,
				member.name,
				member.email,
				member.comNum,
				member.cellphone,
				member.fax,
				member.nickName,
				member.zipCode,
				member.address,
				member.addressDetail,
				member.regTime,
				member.role))
				.from(member)
				.where(member.id.eq(memberId))
				.fetchOne();
		return result;
		 
	}

	@Override
	public Long updateMyEnterPricePrivacy(MyPageEnterPricePrivacyDto myPageEnterPricePrivacyDto, Long memberId) {
		QMember member2 = QMember.member;
		
		long update = queryFactory
				.update(member2)
				.set(member2.name,myPageEnterPricePrivacyDto.getName())
				.set(member2.cellphone, myPageEnterPricePrivacyDto.getCellphone())
				.set(member2.fax, myPageEnterPricePrivacyDto.getFax())
				.set(member2.zipCode,myPageEnterPricePrivacyDto.getZipCode())
				.set(member2.address,myPageEnterPricePrivacyDto.getAddress())
				.set(member2.addressDetail,myPageEnterPricePrivacyDto.getAddressDetail())
				.where(member2.id.eq(memberId))
				.execute();
		
		return update;
	}

	
	
	@Override
	public Page<MyPageStoryListDto> getMyPageStoryList(Long memberId, Pageable pageable) {
		QSharing sharing = QSharing.sharing;
		QStory story = QStory.story;
		QSharingImg sharingImg = QSharingImg.sharingImg;
		
		List<MyPageStoryListDto> content = queryFactory
				 .select(Projections.fields(MyPageStoryListDto.class,
						 story.id,
						 story.member.name,
						// sharingImg.imgUrl,
						 ExpressionUtils.as(JPAExpressions.select(sharingImg.imgUrl)
								 .from(sharingImg)
								 .where(story.member.id.eq(memberId).and(sharingImg.sharing.id.eq(story.sharing.id)))
								 , "imgUrl"),
						 story.member.role,
						 story.member.nickName,
						 story.sharing.name,
						 story.sharing.regTime,
						 story.content
						 ))
				 .from(story)
				 .where(story.member.id.eq(memberId))
				 .orderBy(story.id.asc())
	             .offset(pageable.getOffset())
	             .limit(pageable.getPageSize())
				 .fetch();
		
		long total = queryFactory
				.select(Wildcard.count)
				.from(story)
				.join(story.sharing, sharing)
				.where(story.sharing.member.id.eq(memberId))
				.fetchOne();
		 
		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public MyPageStoryDetailDto getMyPageStoryDetail(Long memberId, Long storyId) {
	 QStory story = QStory.story;
	 QSharingImg sharingImg = QSharingImg.sharingImg;
	 
	 MyPageStoryDetailDto content = queryFactory
			 .select(Projections.fields(MyPageStoryDetailDto.class,
					 story.id,
					 ExpressionUtils.as(JPAExpressions.select(sharingImg.imgUrl)
							 .from(sharingImg)
							 .where(story.member.id.eq(memberId).and(sharingImg.sharing.id.eq(story.sharing.id)))
							 , "imgUrl"),
					 story.sharing.name,
					 story.sharing.area.gugun,
					 story.member.nickName,
					 story.sharing.detail,
					 story.content
					 ))
			 .from(story)
			 .where(story.id.eq(storyId).and(story.id.eq(story.sharing.id)
					 .and(story.sharing.delYn.eq("N").and(story.delYn.eq("N")))))
			 .fetchOne();

		return content;
	}

	@Override
	public Long updateMyPageStoryDetail(MyPageStoryDetailDto myPageStoryDetailDto, Long memberId, Long storyId) {
		QStory story = QStory.story;
		
		long update = queryFactory
				.update(story)
				.set(story.content,myPageStoryDetailDto.getContent())
				.where(story.id.eq(storyId).and(story.member.id.eq(memberId)))
				.execute();
		return update;
	
	}
	
	
	
	
	
	
	
	

	
}
