package com.sharingdonation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.querydsl.core.annotations.QueryProjection;
import com.sharingdonation.entity.Donation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DonationDto {
	
//	public DonationDto(Member member) {
//		this.memberId = member.getId();
//		this.nickName = member.getNickName();
//		
//	}
	private Long id;
	
	private String donationName;
	
	private Long memberId;
	
	private String nickName;
	
	private String donationPerson;
	
	private String donationTel;
	
	private String subject;
	
	private String detail;
	
	private int price;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String zipCode;
	
	private String address;
	
	private String addressDetail;
	
	private int goalPoint;
	
	private LocalDateTime regTime;
	
	private String confirmYn;
	
	private String done;
	
	private String delYn;
	
	private String imgUrl;
	
	private Long heartCount;
	
	private Integer pointSum;
	
	private Integer pointPer;
	
	private String status;
	
	private LocalDateTime updateTime;
	
	
	@QueryProjection
	public DonationDto(Long id, String subject
			, String donationName
			, LocalDate startDate
			, LocalDate endDate, int goalPoint, String imgUrl
			, String nickName
			, String confirmYn
			, String delYn
			, String done
			, LocalDateTime regTime
			, Long heartCount
			, Integer pointSum
			
//			, Integer pointPer
			) { 
		// Member nickName, , String confirmYn, String done, String delYn  String nickName,
		// , Long heartCount
		this.id = id;
		this.subject = subject;
		this.donationName = donationName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.goalPoint = goalPoint;
		this.imgUrl = imgUrl;
		this.nickName = nickName;
		this.confirmYn = confirmYn;
		this.delYn = delYn;
		this.done = done;
		this.regTime = regTime;
		this.heartCount = heartCount;
		this.pointSum = pointSum;
//		this.pointPer = pointPer;
	}

	public static DonationDto valueOf(Donation donation, String imgUrl) {
		String _status = donation.getDone().equals("Y") ? "완료" : (donation.getConfirmYn().equals("Y") ? "진행중": "승인대기");

		return DonationDto.builder()
				.id(donation.getId())
				.subject(donation.getSubject())
//				.categoryName(donation.getCategory().getCategoryName())
//				.regTime(donation.getRegTime().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))
				.regTime(donation.getRegTime())
				.confirmYn(donation.getConfirmYn())
				.done(donation.getDone())
				.imgUrl(imgUrl)
				.status(_status)
//				.point(donation.getPoint())
				.build();
	}
	public static DonationDto valueOf(Donation donation) {
		String _status = donation.getDone().equals("Y") ? "완료" : (donation.getConfirmYn().equals("Y") ? "진행중": "승인대기");
		String _startDate = null;
		String _endDate = null;
		if (donation.getStartDate() != null) _startDate = donation.getStartDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
		if (donation.getEndDate() != null) _endDate = donation.getEndDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));

		return DonationDto.builder()
				.id(donation.getId())
				.donationName(donation.getDonationName())
				
				.address(donation.getAddress())
				.addressDetail(donation.getAddressDetail())
				.confirmYn(donation.getConfirmYn())
				.delYn(donation.getDelYn())
				.donationPerson(donation.getDonationPerson())
				.donationTel(donation.getDonationTel())
				.done(donation.getDone())
				.goalPoint(donation.getGoalPoint())
				.nickName(donation.getMember().getNickName())
				.price(donation.getPrice())
				.subject(donation.getSubject())
				.updateTime(donation.getUpdateTime())
				.zipCode(donation.getZipCode())
				
				.regTime(donation.getRegTime())
				.status(_status)
				.startDate(donation.getStartDate())
				.endDate(donation.getEndDate())
				//.startDate(_startDate)
				.build();
	}
	
}
