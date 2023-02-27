package com.sharingdonation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharingdonation.entity.SharingBoardComment;

public interface SharingBoardCommentRepository extends JpaRepository<SharingBoardComment, Long>{

}
