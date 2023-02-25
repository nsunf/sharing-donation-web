package com.sharingdonation.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sharingdonation.dto.DonationBoardDto;
import com.sharingdonation.dto.QDonationBoardDto;
import com.sharingdonation.entity.DonationBoard;
import com.sharingdonation.entity.QDonation;
import com.sharingdonation.entity.QDonationBoard;
import com.sharingdonation.entity.QDonationBoardImg;

public class DonationBoardRepositoryCustomImpl implements DonationBoardRepositoryCustom{

	
private JPAQueryFactory queryFactory;
	
	public DonationBoardRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	@Override
	public Page<DonationBoard> getAdminDonationBoardPage(Pageable pageable) {
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
	public Page<DonationBoardDto> getDonationBoardPage(Pageable pageable) {
		QDonationBoard donationBoard = QDonationBoard.donationBoard;
		QDonationBoardImg donationBoardImg = QDonationBoardImg.donationBoardImg;
		QDonation donation = QDonation.donation;
		
		List<DonationBoardDto> content = queryFactory.select(
				new QDonationBoardDto(
						donationBoard.id,
						donationBoard.donation.id,
						donationBoard.subject,
						donationBoard.content,
						donationBoard.regTime,
						donationBoardImg.imgUrl
						)
				)
				.from(donationBoardImg)
				.join(donationBoardImg.donationBoard, donationBoard)
				.where(donationBoardImg.repimgYn.eq("Y"))
				.orderBy(donationBoard.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		Long total = queryFactory.select(Wildcard.count)
				.from(donationBoardImg)
				.join(donationBoardImg.donationBoard, donationBoard)
				.where(donationBoardImg.repimgYn.eq("Y"))
				.fetchOne();
		return new PageImpl<>(content, pageable, total);
	}

}
