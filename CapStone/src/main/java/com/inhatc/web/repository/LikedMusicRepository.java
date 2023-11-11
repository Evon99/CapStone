package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.web.entity.LikedMusic;

public interface LikedMusicRepository extends JpaRepository<LikedMusic, Long>{

	boolean existsByMusic_IdAndMember_Id(Long musicId, Long memberId);
	
	List<LikedMusic> findByMember_Id(Long memberId);
}
 