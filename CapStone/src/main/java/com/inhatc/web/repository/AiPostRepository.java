package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inhatc.web.entity.AiPost;
import com.inhatc.web.entity.RequestPost;
import com.inhatc.web.entity.TipPost;

public interface AiPostRepository extends JpaRepository<AiPost, Long>{

	@Query("SELECT ap FROM AiPost ap ORDER BY ap.regTime DESC, ap.id ASC")
    Page<AiPost> findAllByOrderByRegTimeDescAndIdAsc(Pageable pageable);
	
	@Query("SELECT ap FROM AiPost ap WHERE LOWER(ap.title) LIKE LOWER(CONCAT('%', :title, '%')) ORDER BY ap.regTime DESC, ap.id ASC")
	Page<AiPost> findAllByTitleOrderByRegTimeDescAndIdAsc(@Param("title") String title, Pageable pageable);
	
	@Query("SELECT ap FROM RequestPost ap " +
		       "JOIN ap.memberDetail md " +
		       "WHERE md.nickname = :nickname " +
		       "ORDER BY ap.regTime DESC")
	Page<AiPost> findAllByNicknameOrderByRegTimeDesc(@Param("nickname") String nickname, Pageable pageable);
	
	AiPost findById(long id);
	
	@Query("SELECT a.oriVoiceName FROM AiPost a WHERE a.voiceName = :voiceName")
    String findOriVoiceNameByVoiceName(@Param("voiceName") String voiceName);
}
 