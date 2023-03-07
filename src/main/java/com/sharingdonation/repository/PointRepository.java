package com.sharingdonation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sharingdonation.entity.Point;

public interface PointRepository extends JpaRepository<Point, Long> {
	@Query("select coalesce(sum(p.point),0) as point_sum from Point p where p.donation.id = :donationId")
	Integer selectTotalPoint(@Param("donationId") Long donationId);
	
	@Query(value="select coalesce(sum(if(p.move_type = 'MINUS' ,  p.point * -1 , p.point)), 0) as point_sum from Point p where p.member_id = :memberId order by point_id asc", nativeQuery = true)
	Long pointSearch(@Param("memberId") Long memberId);
}