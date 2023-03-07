package com.sharingdonation.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.sharingdonation.constant.MoveType;
import com.sharingdonation.entity.Point;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDto {
	private Long id;
	
	private MoveType moveType;
	
	@NotBlank(message = "포인트는 필수 입력 값입니다.")
	private String point;
	
	private Long donationId;
	
	private Long memberId;
	
	private String sharingId;
	
	private String comment;
	
	private LocalDateTime regTime;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Point createPoint() {
		return modelMapper.map(this,  Point.class);
	}
	
	public static PointDto of(Point point) {
		return modelMapper.map(point, PointDto.class);
	}
}
