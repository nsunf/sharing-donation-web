package com.sharingdonation.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sharingdonation.dto.QSharingStoryDto;
import com.sharingdonation.dto.QStoryDto;
import com.sharingdonation.dto.SharingStoryDto;
import com.sharingdonation.dto.StoryDto;
import com.sharingdonation.entity.QMember;
import com.sharingdonation.entity.QSharing;
import com.sharingdonation.entity.QSharingImg;
import com.sharingdonation.entity.QStory;

public class StoryRepositoryCustomImpl implements StoryRepositoryCustom {
	private JPAQueryFactory qf;
	
	public StoryRepositoryCustomImpl(EntityManager em) {
		this.qf = new JPAQueryFactory(em);
	}

	@Override
	public Page<SharingStoryDto> getAdminStoryPage(String search, Pageable pageable) {
		QSharing sharing = QSharing.sharing;
		QSharingImg sharingImg = QSharingImg.sharingImg;
		QStory story = QStory.story;
		
		List<SharingStoryDto> contents = qf
				.select(new QSharingStoryDto(sharing, sharingImg, story.id.count()))
				.from(sharingImg)
				.rightJoin(sharingImg.sharing, sharing)
				.leftJoin(story)
				.on(story.sharing.eq(sharing))
				.where(sharingImg.repimgYn.eq("Y"))
				.where(sharing.name.contains(search))
				.where(sharing.delYn.eq("N"))
				.where(story.delYn.eq("N"))
				.groupBy(sharing, sharingImg)
				.orderBy(sharing.endDate.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = qf
				.select(new QSharingStoryDto(sharing, sharingImg, story.id.count()))
				.from(sharingImg)
				.rightJoin(sharingImg.sharing, sharing)
				.leftJoin(story)
				.on(story.sharing.eq(sharing))
				.where(sharingImg.repimgYn.eq("Y"))
				.where(sharing.name.contains(search))
				.where(sharing.delYn.eq("N"))
				.where(story.delYn.eq("N"))
				.groupBy(sharing, sharingImg)
				.orderBy(sharing.endDate.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchCount();

		return new PageImpl<>(contents, pageable, total);
	}
	
	@Override
	public List<StoryDto> getAdminStoryList(Long sharingId) {
		QSharing sharing = QSharing.sharing;
		QStory story = QStory.story;
		QMember member = QMember.member;

		List<StoryDto> contents = qf
				.select(new QStoryDto(sharing, story, member))
				.from(story)
				.join(sharing)
				.on(sharing.id.eq(story.sharing.id))
				.join(member)
				.on(story.member.id.eq(member.id))
				.where(sharing.id.eq(sharingId))
				.where(story.delYn.eq("N"))
				.fetch();

		return contents;
	}
	
	@Override
	public StoryDto getStoryDto(Long storyId) {
		QStory story = QStory.story;
		QSharing sharing = QSharing.sharing;
		QMember member = QMember.member;
		
		StoryDto result = qf
				.select(new QStoryDto(sharing, story, member))
				.from(story)
				.join(sharing)
				.on(sharing.id.eq(story.sharing.id))
				.join(member)
				.on(story.member.id.eq(member.id))
				.where(story.id.eq(storyId))
				
				.fetchOne();


		return result;
	}
}
 