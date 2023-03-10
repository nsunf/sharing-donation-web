package com.sharingdonation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.Area;

public interface AreaRepository extends JpaRepository<Area, Long> {
	Area findByGugun(String gugn);
}
