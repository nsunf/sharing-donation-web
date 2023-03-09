package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sharingdonation.entity.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> 
//	QuerydslPredicateExecutor<Donation> 
	, DonationRepositoryCustom 
	{

	List<Donation> findAllByIdIn(List<Long> donatinIdList);
	
	Donation findAllByIdAndMemberId(Long donatinId, Long memberId);
	
	
	Page<Donation> findByMemberIdAndDelYnOrderByRegTimeDesc(Long memberId, String delYn, Pageable pageable);
	
	List<Donation> findByDone(String done);
	Long countByConfirmYnAndDone(String confirmYn, String done);
	
	@Query(value="select donation_id, subject, detail, goal_point, start_date, price, end_date, donation_name, reg_time, update_time, zip_code, address, address_detail, confirm_yn, done, del_yn, donation_person, donation_tel, member_id, (select coalesce(sum(point),0) from point p where d.donation_id = p.donation_id) as point_sum from donation d where d.confirm_yn = 'Y' and done = 'N' and del_yn = 'N' order by RAND() LIMIT 1", nativeQuery = true)
	Donation findMainDonation();
}
