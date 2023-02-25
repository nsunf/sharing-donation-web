package com.sharingdonation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingBoard;

public interface SharingBoardRepository extends JpaRepository <SharingBoard, Long> {

}
