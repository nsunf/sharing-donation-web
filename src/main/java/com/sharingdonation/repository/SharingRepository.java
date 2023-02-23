package com.sharingdonation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.Sharing;

public interface SharingRepository extends JpaRepository<Sharing, Long> {

}
