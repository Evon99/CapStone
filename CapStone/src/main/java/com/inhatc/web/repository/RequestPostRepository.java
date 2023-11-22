package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inhatc.web.entity.Report;
import com.inhatc.web.entity.RequestPost;

public interface RequestPostRepository extends JpaRepository<RequestPost, Long>{

	@Query("SELECT rp FROM RequestPost rp ORDER BY rp.regTime DESC, rp.id ASC")
    Page<RequestPost> findAllByOrderByRegTimeDescAndIdAsc(Pageable pageable);
	
	@Query("SELECT rp FROM RequestPost rp WHERE LOWER(rp.title) LIKE LOWER(CONCAT('%', :title, '%')) ORDER BY rp.regTime DESC, rp.id ASC")
	Page<RequestPost> findAllByTitleOrderByRegTimeDescAndIdAsc(@Param("title") String title, Pageable pageable);

	@Query("SELECT rp FROM RequestPost rp " +
		       "JOIN rp.memberDetail md " +
		       "WHERE md.nickname = :nickname " +
		       "ORDER BY rp.regTime DESC")
	Page<RequestPost> findAllByNicknameOrderByRegTimeDesc(@Param("nickname") String nickname, Pageable pageable);
	
	RequestPost findById(long id);
	
	Page<RequestPost> findByTitleContaining(String title, Pageable pageable);
}
 