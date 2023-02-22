package com.sharingdonation.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="shring_board_comment")
@Getter
@Setter
@ToString
public class SharingBoardComment {
	@Id
	@Column(name="sharing_board_comment")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="shring_board_id")
	private SharingBoard sharingBoard;
	
	private Long memberId;
	
	private String comment;
	
	private LocalDateTime regTime;
}
