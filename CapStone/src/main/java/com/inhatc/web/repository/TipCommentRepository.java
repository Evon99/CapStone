package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.web.entity.TipComment;

public interface TipCommentRepository extends JpaRepository<TipComment, Long>{

	List<TipComment> findByTipPost_IdOrderByRegTimeAsc(Long requestPostId, Pageable pageable);

}
 