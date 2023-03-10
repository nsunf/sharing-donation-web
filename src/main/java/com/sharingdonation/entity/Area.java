package com.sharingdonation.entity;

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
@Table(name="area")
@Getter
@Setter
@ToString
public class Area {
	@Id
	@Column(name="area_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable= false, columnDefinition = "varchar(50)")
	private String sido;
	
	@Column(nullable = false, columnDefinition = "varchar(50)")
	private String gugun;
	
}
