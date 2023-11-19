package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.web.entity.AiComment;

public interface AiPostCommentRepository extends JpaRepository<AiComment, Long>{

	List<AiComment> findByAiPost_IdOrderByRegTimeAsc(Long aiPostId, Pageable pageable);

}
 