package com.sharingdonation.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sharingdonation.dto.DonationBoardDto;
import com.sharingdonation.dto.DonationBoardSearchDto;
import com.sharingdonation.dto.QDonationBoardDto;
import com.sharingdonation.entity.DonationBoard;
import com.sharingdonation.entity.QDonation;
//import com.sharingdonation.entity.QDonation;
import com.sharingdonation.entity.QDonationBoard;
import com.sharingdonation.entity.QDonationBoardComment;
import com.sharingdonation.entity.QDonationBoardHeart;
import com.sharingdonation.entity.QDonationBoardImg;

public class DonationBoardRepositoryCustomImpl implements DonationBoardRepositoryCustom{

	
private JPAQueryFactory queryFactory;
	
	public DonationBoardRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchByLike(String searchQuery) {//String searchBy, 
		if (! searchQuery.equals("") ) {
			return QDonation.donation.subject.like("%" + searchQuery + "%");
//		} else if(StringUtils.equals("d",searchBy)) {
//			return QDonation.donation.donationName.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	@Override
	public Page<DonationBoard> getAdminDonationBoardPage(DonationBoardSearchDto donationBoardSearchDto, Pageable pageable) {
		List<DonationBoard> content = queryFactory
				.selectFrom(QDonationBoard.donationBoard)
				.orderBy(QDonationBoard.donationBoard.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		Long total = queryFactory.select(Wildcard.count).from(QDonationBoard.donationBoard)
				.fetchOne();
		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public Page<DonationBoardDto> getDonationBoardPage(DonationBoardSearchDto donationBoardSearchDto, Pageable pageable) {
		QDonationBoard donationBoard = QDonationBoard.donationBoard;
		QDonationBoardImg donationBoardImg = QDonationBoardImg.donationBoardImg;
//		QDonation donation = QDonation.donation;
		QDonationBoardComment donationBoardComment = QDonationBoardComment.donationBoardComment;
		QDonationBoardHeart donationBoardHeart = QDonationBoardHeart.donationBoardHeart;
		
		StringExpression formattedDatetime = Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", donationBoard.regTime);  //query에서 일자바
		List<DonationBoardDto> content = queryFactory
				.select(
						new QDonationBoardDto(
							donationBoard.id,
							donationBoard.donation.id,
							donationBoard.subject,
							donationBoard.content,
							formattedDatetime,
							donationBoardImg.imgUrl, 
							ExpressionUtils.as(JPAExpressions.select(donationBoardComment.count()).from(donationBoardComment).where(donationBoardImg.donationBoard.eq(donationBoardComment.donationBoard)), "commentCount"),
							ExpressionUtils.as(JPAExpressions.select(donationBoardHeart.count()).from(donationBoardHeart).where(donationBoardImg.donationBoard.eq(donationBoardHeart.donationBoard)), "donationBoardheartCount"),
							donationBoard.donation.donationPerson,
							donationBoard.donation.startDate,
							donationBoard.donation.endDate
							
						)
				)
				.from(donationBoardImg)
				.join(donationBoardImg.donationBoard, donationBoard)
				.where(donationBoardImg.repimgYn.eq("Y"))
				.where(searchByLike(donationBoardSearchDto.getSearchQuery()))
				.orderBy(donationBoard.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		Long total = queryFactory.select(Wildcard.count)
				.from(donationBoardImg)
				.join(donationBoardImg.donationBoard, donationBoard)
				.where(donationBoardImg.repimgYn.eq("Y"))
				.where(searchByLike(donationBoardSearchDto.getSearchQuery()))
				.fetchOne();
		return new PageImpl<>(content, pageable, total);
	}

}
