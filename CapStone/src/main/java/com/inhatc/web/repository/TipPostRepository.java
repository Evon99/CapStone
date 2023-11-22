package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inhatc.web.entity.RequestPost;
import com.inhatc.web.entity.TipPost;

public interface TipPostRepository extends JpaRepository<TipPost, Long>{

	@Query("SELECT tp FROM TipPost tp ORDER BY tp.regTime DESC, tp.id ASC")
    Page<TipPost> findAllByOrderByRegTimeDescAndIdAsc(Pageable pageable);
	
	@Query("SELECT tp FROM TipPost tp WHERE LOWER(tp.title) LIKE LOWER(CONCAT('%', :title, '%')) ORDER BY tp.regTime DESC, tp.id ASC")
	Page<TipPost> findAllByTitleOrderByRegTimeDescAndIdAsc(@Param("title") String title, Pageable pageable);
	
	@Query("SELECT tp FROM TipPost tp " +
		       "JOIN tp.memberDetail md " +
		       "WHERE md.nickname = :nickname " +
		       "ORDER BY tp.regTime DESC")
	Page<TipPost> findAllByNicknameOrderByRegTimeDesc(@Param("nickname") String nickname, Pageable pageable);
	
	TipPost findById(long id);
}
 