
package com.sharingdonation.repository;

import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
//import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sharingdonation.dto.DonationDto;
import com.sharingdonation.dto.SearchDto;
import com.sharingdonation.dto.SharingAdminSearchDto;
import com.sharingdonation.dto.ListDonationDto;
import com.sharingdonation.dto.QDonationDto;
import com.sharingdonation.dto.QListDonationDto;
import com.sharingdonation.entity.Donation;
import com.sharingdonation.entity.QDonation;
import com.sharingdonation.entity.QDonationHeart;
import com.sharingdonation.entity.QDonationImg;
import com.sharingdonation.entity.QMember;
import com.sharingdonation.entity.QPoint;
import com.sharingdonation.entity.QSharing;

public class DonationRepositoryCustomImpl implements DonationRepositoryCustom{

	private JPAQueryFactory queryFactory;

	public DonationRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("d", searchBy) ) {
			return QDonation.donation.donationName.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("d",searchBy)) {
			return QDonation.donation.subject.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	private Predicate searchOption(SearchDto donationSearchDto) {
		QDonation donation = QDonation.donation;
		String searchTerm = donationSearchDto.getSearchQuery();
		BooleanBuilder builder = new BooleanBuilder();
		
		switch (donationSearchDto.getSearchBy()) {
		case "subject":
			builder.and(donation.subject.like("%" + searchTerm + "%"));
			break;
		case "donationName":
			builder.and(donation.donationName.contains(searchTerm));
			break;
		case "author":
			builder.and(donation.member.nickName.contains(searchTerm));
			break;
		}
		
		switch (donationSearchDto.getStatus()) {
		case "outstanding":
			builder.and(donation.done.eq("N")).and(donation.confirmYn.eq("N"));
			break;
		case "proceeding":
			builder.and(donation.done.eq("N")).and(donation.confirmYn.eq("Y"));
			break;
		case "complete":
			builder.and(donation.done.eq("Y"));
			break;
		default:
			break;
		}
		
		builder.and(donation.delYn.eq("N"));
		
		return builder;
	}

	@Override
	public Page<DonationDto> getAdminListDonationPage(SearchDto donationSearchDto, Pageable pageable) {
		QDonation donation = QDonation.donation;
		QDonationImg donationImg = QDonationImg.donationImg;
		QMember member = QMember.member;
		QDonationHeart subDonationHeart = QDonationHeart.donationHeart;
		QPoint subPoint = QPoint.point1;
		
		List<DonationDto> content = queryFactory
					.select(
							new QDonationDto (
							donation.id,
							donation.subject,
							donation.donationName,
							donation.startDate,
							donation.endDate,
							donation.goalPoint,
							donationImg.imgUrl, 
							member.nickName,
							donation.confirmYn,
							donation.delYn,
							donation.done,
							donation.regTime,
//							JPAExpressions.select(subDonationHeart.count()).from(subDonationHeart).where(donationImg.donation.eq(subDonationHeart.donation)).as("heartCount")
							ExpressionUtils.as(JPAExpressions.select(subDonationHeart.count()).from(subDonationHeart).where(donationImg.donation.eq(subDonationHeart.donation)), "heartCount"),
							ExpressionUtils.as(JPAExpressions.select(subPoint.point.sum().coalesce(0)).from(subPoint).where(donationImg.donation.eq(subPoint.donation)), "pointSum")
							)
					)
					.from(donation)
//					.join(donation, donationImg.donation)
					.leftJoin(donationImg).on(donation.donation.eq(donationImg.donation), donationImg.repimgYn.eq("Y"))
					.leftJoin(member).on(donation.member.eq(member.member))
//					.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
					.where(searchOption(donationSearchDto))
					.orderBy(donation.id.desc())
					.offset(pageable.getOffset())
					.limit(pageable.getPageSize())
					.fetch();
					
		long total = queryFactory.select(Wildcard.count)
					.from(donation)
					.leftJoin(donationImg).on(donation.donation.eq(donationImg.donation), donationImg.repimgYn.eq("Y"))
					.leftJoin(member).on(donation.member.eq(member.member))
//					.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
					.where(searchOption(donationSearchDto))
					.fetchOne();
		return new PageImpl<>(content, pageable, total);
	}
	
	@Override
	public Page<ListDonationDto> getListDonationPage(SearchDto donationSearchDto, Pageable pageable) {
		QDonation donation = QDonation.donation;
		QDonationImg donationImg = QDonationImg.donationImg;
		QMember member = QMember.member;
		QDonationHeart subDonationHeart = QDonationHeart.donationHeart;
		QPoint subPoint = QPoint.point1;
		
		List<ListDonationDto> content = queryFactory
				.select(
				new QListDonationDto(
						donation.id,
						donation.subject,
						donation.startDate,
						donation.endDate,
						donation.goalPoint,
						donationImg.imgUrl, 
						member.nickName,
//						JPAExpressions.select(subDonationHeart.count()).from(subDonationHeart).where(donationImg.donation.eq(subDonationHeart.donation)).as("heartCount")
						ExpressionUtils.as(JPAExpressions.select(subDonationHeart.count()).from(subDonationHeart).where(donationImg.donation.eq(subDonationHeart.donation)), "heartCount"),
						ExpressionUtils.as(JPAExpressions.select(subPoint.point.sum().coalesce(0)).from(subPoint).where(donationImg.donation.eq(subPoint.donation)), "pointSum")
						)
				)
				.from(donation)
				.leftJoin(donationImg).on(donation.eq(donationImg.donation), donationImg.repimgYn.eq("Y"))
				.leftJoin(member).on(donation.member.eq(member))
				.where(donation.donationName.like("%" + donationSearchDto.getSearchQuery() + "%")
						.or(donation.subject.like("%" + donationSearchDto.getSearchQuery() + "%"))
						.or(member.nickName.like("%" + donationSearchDto.getSearchQuery() + "%")))
				.where(donation.confirmYn.eq("Y")
		                .and(donation.done.eq("N"))
		                .and(donation.delYn.eq("N")))
//				.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
				.where(donation.endDate.gt(LocalDate.now()))
				.orderBy(donation.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
//			.from(donation)
//			.innerJoin(donation.member, member).on(member.id.eq(donation.member.id))
//			.innerJoin(donationImg).on(donation.id.eq(donationImg.donation.id))
//			.where(donationImg.repimgYn.eq("Y"))
//			.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
//			.orderBy(donation.id.desc())
//			.offset(pageable.getOffset())
//			.limit(pageable.getPageSize())
//			.fetch();
		

//					.select(
//						new QListDonationDto(
////							Projections.constructor(ListDonationDto.class,
//								donation.id,
//								donation.subject,
//								donation.startDate,
//								donation.endDate,
//								donation.goalPoint
//								, donationImg.imgUrl
////								, member.nickName
////								, JPAExpressions.select(subDonationHeart.count()).from(subDonationHeart).where(donationImg.donation.eq(subDonationHeart.donation)).as("heartCount")
////								, ExpressionUtils.as(JPAExpressions.select(Wildcard.count).from(donationHeartDto), "heartCount")
//								)
//					)
//					.from(donationImg)
//					.join(donationImg.donation, donation)
////					.join(donation.member, member)
//					.where(donationImg.repimgYn.eq("Y"))
//					.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
//					.orderBy(donation.id.desc())
//					.offset(pageable.getOffset())
//					.limit(pageable.getPageSize())
//					.fetch();
		

		
		long total = queryFactory
				.select(Wildcard.count)
				.from(donation)
				.leftJoin(donationImg).on(donation.eq(donationImg.donation), donationImg.repimgYn.eq("Y"))
				.leftJoin(member).on(donation.member.eq(member))
//				.where(donationImg.repimgYn.eq("Y"))
//				.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
				.where(donation.donationName.like("%" + donationSearchDto.getSearchQuery() + "%")
						.or(donation.subject.like("%" + donationSearchDto.getSearchQuery() + "%"))
						.or(member.nickName.like("%" + donationSearchDto.getSearchQuery() + "%")))
				.where(donation.confirmYn.eq("Y")
		                .and(donation.done.eq("N"))
		                .and(donation.delYn.eq("N")))
				.where(donation.endDate.gt(LocalDate.now()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
		
}
