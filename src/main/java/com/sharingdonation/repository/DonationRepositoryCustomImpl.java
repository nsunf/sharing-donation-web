//package com.sharingdonation.repository;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.thymeleaf.util.StringUtils;
//
//import com.querydsl.core.types.ExpressionUtils;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.core.types.dsl.Wildcard;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.sharingdonation.dto.DonationSearchDto;
//import com.sharingdonation.dto.ListDonationDto;
//import com.sharingdonation.dto.QListDonationDto;
//import com.sharingdonation.entity.Donation;
//import com.sharingdonation.entity.QDonation;
//import com.sharingdonation.entity.QDonationHeart;
//import com.sharingdonation.entity.QDonationImg;
//
//public class DonationRepositoryCustomImpl implements DonationRepositoryCustom{
//
//	private JPAQueryFactory queryFactory;
//
//	public DonationRepositoryCustomImpl(EntityManager em) {
//		this.queryFactory = new JPAQueryFactory(em);
//	}
//	
//	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
//		if(StringUtils.equals("", searchBy) ) {
//			return QDonation.donation.donationName.like("%" + searchQuery + "%");
//		} else if(StringUtils.equals("",searchBy)) {
//			return QDonation.donation.subject.like("%" + searchQuery + "%");
//		}
//		return null;
//	}
//
//	@Override
//	public Page<Donation> getAdminDonationPage(DonationSearchDto donationSearchDto, Pageable pageable) {
//		List<Donation> content = queryFactory
//					.selectFrom(QDonation.donation)
//					.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
//					.orderBy(QDonation.donation.id.desc())
//					.offset(pageable.getOffset())
//					.limit(pageable.getPageSize())
//					.fetch();
//					
//		long total = queryFactory.select(Wildcard.count).from(QDonation.donation)
//					.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
//					.fetchOne();
//		return new PageImpl<>(content, pageable, total);
//	}
//	
//	@Override
//	public Page<ListDonationDto> getListDonationPage(DonationSearchDto donationSearchDto, Pageable pageable) {
//		QDonation donation = QDonation.donation;
//		QDonationImg donationImg = QDonationImg.donationImg;
//		QMember member = QMember.member;
//		
//		List<ListDonationDto> content = queryFactory
//					.select(
//						new QListDonationDto(
//								donation.id,
//								donation.subject,
//								donation.startDate,
//								donation.endDate,
//								donation.goalPoint,
//								donationImg.imgUrl,
//								donation.nickName
////								, ExpressionUtils.as(JPAExpressions.select(Wildcard.count).from(donationHeartDto), "heartCount")
//								) // todo 좋아요 추가해야
//					)
//					.from(donation)
//					.join(donationImg.donation, donation)
//					.join(donation.donation, member);
//					.where(donationImg.repimgYn.eq("Y"))
//					.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
//					.orderBy(donation.id.desc())
//					.offset(pageable.getOffset())
//					.limit(pageable.getPageSize())
//					.fetch();
//		
//		long total = queryFactory
//				.select(Wildcard.count)
//				.from(donation)
////				.join(donationImg.donation, donation)
////				.where(donationImg.repimgYn.eq("Y"))
//				.where(searchByLike(donationSearchDto.getSearchBy(), donationSearchDto.getSearchQuery()))
//				.fetchOne();
//		
//		return new PageImpl<>(content, pageable, total);
//	}
//		
//}
