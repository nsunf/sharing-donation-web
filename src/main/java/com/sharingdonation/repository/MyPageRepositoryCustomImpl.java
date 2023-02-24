package com.sharingdonation.repository;

 

import javax.persistence.EntityManager;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sharingdonation.constant.MoveType;
import com.sharingdonation.dto.MyPageMainDto;
import com.sharingdonation.dto.MyPagePrivacyDto;
import com.sharingdonation.entity.QMember;
import com.sharingdonation.entity.QPoint;
import com.sharingdonation.entity.QSharing;
import com.sharingdonation.entity.QStory;


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
				 .select(Projections.fields(MyPageMainDto.class,
						 member.id,
						 member.point,
						 ExpressionUtils.as(JPAExpressions.select(sharing.id.count())
								 .from(sharing)
								 .where(sharing.member.id.eq(memberId).and(sharing.delYn.eq("no")))
								 ,"share_reg"),
						 ExpressionUtils.as(JPAExpressions.select(story.id.count())
								 .from(story)
								 .where(story.member.id.eq(memberId).and(story.chooseYn.eq("y")))
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
						 member.regTime
						 
						 ))
				 .from(member)
				 .where(member.id.eq(memberId))
				 .fetchFirst();
		 
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
				member.regTime))
				.from(member)
				.where(member.id.eq(memberId))
				.fetchFirst();
		return result;
	}
	
	

	
}
