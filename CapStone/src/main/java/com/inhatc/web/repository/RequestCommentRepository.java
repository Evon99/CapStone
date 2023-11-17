package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.web.entity.RequestComment;

public interface RequestCommentRepository extends JpaRepository<RequestComment, Long>{

	List<RequestComment> findByRequestPost_IdOrderByRegTimeAsc(Long requestPostId, Pageable pageable);

}
 