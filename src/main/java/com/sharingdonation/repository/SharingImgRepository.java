package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingImg;

public interface SharingImgRepository extends JpaRepository<SharingImg, Long> {
	List<SharingImg> findBySharingId(Long sharingId);
	void deleteAllBySharingId(Long sharingId);
}
