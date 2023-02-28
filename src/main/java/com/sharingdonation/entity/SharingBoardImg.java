package com.sharingdonation.entity;

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
@Table(name="sharing_board_img")
@Getter
@Setter
@ToString
public class SharingBoardImg {
	@Id
	@Column(name="sharing_board_img_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sharing_board_id")
	private SharingBoard sharingBoard;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String imgName;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String oriImgName;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String imgUrl;
	
	@Column(nullable = false, columnDefinition = "char(1) default 'N'")
	private String repimgYn;
	
}
