package com.sharingdonation.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sharingdonation.dto.QSharingDto;
import com.sharingdonation.dto.SharingDto;
import com.sharingdonation.entity.QMember;
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
				.join(sharingImg)
				.on(sharingImg.sharing.id.eq(sharing.id))
				.join(story)
				.on(story.sharing.id.eq(sharing.id))
				.where(story.member.id.eq(memberId))
				.where(sharingImg.repimgYn.eq("Y"))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = qf
				.select(new QSharingDto(sharing, sharingImg, story))
				.from(sharing)
				.join(sharingImg)
				.on(sharingImg.sharing.id.eq(sharing.id))
				.join(story)
				.on(story.sharing.id.eq(sharing.id))
				.where(story.member.id.eq(memberId))
				.where(sharingImg.repimgYn.eq("Y"))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchCount();
		
		return new PageImpl<>(contents, pageable, total);
	}

}
