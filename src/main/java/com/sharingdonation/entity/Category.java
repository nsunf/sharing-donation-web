package com.sharingdonation.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="category")
@Getter
@Setter
@ToString
public class Category {
	@Id
	@Column(name="category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String categoryName;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String imgName;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String imgUrl;
	
	@Column(nullable = false, columnDefinition = "char(1) default 'N'")
	private String delYn;
}
