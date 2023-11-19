package com.inhatc.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.web.entity.RequestComment;

public interface RequestCommentRepository extends JpaRepository<RequestComment, Long>{

	//List<RequestComment> findByRequestPost_IdOrderByRegTimeAsc(Long requestPostId, Pageable pageable);
	
	Page<RequestComment> findByRequestPost_IdOrderByRegTimeAsc(Long requestPostId, Pageable pageable);

}
 