package com.sharingdonation.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sharingdonation.dto.SharingBoardFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="sharing_board")
@Getter
@Setter
@ToString
public class SharingBoard {
	@Id
	@Column(name="sharing_board_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sharing_id")
	private Sharing sharing; //나눔상품
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String subject;
	
	@Column(nullable = false, columnDefinition = "text")
	private String content;
	
	@Column(nullable = false)
	private LocalDateTime regTime;
	
	public void updateSharingBoard(SharingBoardFormDto sharingBoardFormDto) {
		this.subject = sharingBoardFormDto.getSubject();
		this.content = sharingBoardFormDto.getContent();
	}
	
}
