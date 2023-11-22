package com.inhatc.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.web.entity.TipComment;

public interface TipCommentRepository extends JpaRepository<TipComment, Long>{

//	List<TipComment> findByTipPost_IdOrderByRegTimeAsc(Long requestPostId, Pageable pageable);
	
	Page<TipComment> findByTipPost_IdOrderByRegTimeAsc(Long requestPostId, Pageable pageable);

}
 