package com.sharingdonation.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sharingdonation.dto.QSharingDto;
import com.sharingdonation.dto.SharingAdminSearchDto;
import com.sharingdonation.dto.SharingDto;
import com.sharingdonation.entity.QSharing;
import com.sharingdonation.entity.QSharingImg;
import com.sharingdonation.entity.QStory;

public class SharingRepositoryCustomImpl implements SharingRepositoryCustom {
	
	private JPAQueryFactory qf;
	
	public SharingRepositoryCustomImpl(EntityManager em) {
		this.qf = new JPAQueryFactory(em);
	}

	@Override
	public Page<SharingDto> getAdoptedSharingList(Long memberId, Pageable pageable) {
		QSharing sharing = QSharing.sharing;
		QSharingImg sharingImg = QSharingImg.sharingImg;
		QStory story = QStory.story;
//		QMember member = QMember.member;

		List<SharingDto> contents = qf
				.select(new QSharingDto(sharing, sharingImg, story))
				.from(sharing)
				.leftJoin(sharingImg)
				.on(sharingImg.sharing.id.eq(sharing.id))
				.leftJoin(story)
				.on(story.sharing.id.eq(sharing.id))
				.where(story.member.id.eq(memberId).and(story.chooseYn.eq("Y")))
				.where(sharingImg.repimgYn.eq("Y"))
				.where(sharing.done.eq("Y"))
				.orderBy(story.regTime.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = qf
				.select(new QSharingDto(sharing, sharingImg, story))
				.from(sharing)
				.leftJoin(sharingImg)
				.on(sharingImg.sharing.id.eq(sharing.id))
				.leftJoin(story)
				.on(story.sharing.id.eq(sharing.id))
				.where(story.member.id.eq(memberId))
				.where(sharingImg.repimgYn.eq("Y"))
				.where(sharing.done.eq("Y"))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchCount();
		
		return new PageImpl<>(contents, pageable, total);
	}
	

	private Predicate searchOption(SharingAdminSearchDto searchDto) {
		QSharing sharing = QSharing.sharing;
		String searchTerm = searchDto.getSearch();
		BooleanBuilder builder = new BooleanBuilder();
		
		switch (searchDto.getFilter()) {
		case "title":
			builder.and(sharing.name.contains(searchTerm));
			break;
		case "content":
			builder.and(sharing.detail.contains(searchTerm));
			break;
		case "author":
			builder.and(sharing.member.name.contains(searchTerm));
			break;
		}
		
		switch (searchDto.getStatus()) {
		case "outstanding":
			builder.and(sharing.done.eq("N")).and(sharing.confirmYn.eq("N"));
			break;
		case "proceeding":
			builder.and(sharing.done.eq("N")).and(sharing.confirmYn.eq("Y"));
			break;
		case "complete":
			builder.and(sharing.done.eq("Y"));
			break;
		default:
			break;
		}
		
		builder.and(sharing.delYn.eq("N"));
		
		return builder;
	}
	
	@Override
	public Page<SharingDto> getAdminSharingDtoList(SharingAdminSearchDto searchDto, Pageable pageable) {
		QSharing sharing = QSharing.sharing;
		
		List<SharingDto> content = qf
				.select(new QSharingDto(sharing))
				.from(sharing)
				.where(searchOption(searchDto))
				.orderBy(sharing.regTime.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		Long count = qf
				.select(sharing.count())
				.from(sharing)
				.where(searchOption(searchDto))
				.fetchOne();

		return new PageImpl<>(content, pageable, count);
	}
}
