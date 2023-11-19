package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inhatc.web.entity.AiPost;

public interface AiPostRepository extends JpaRepository<AiPost, Long>{

	@Query("SELECT ap FROM AiPost ap ORDER BY ap.regTime DESC, ap.id ASC")
    List<AiPost> findAllByOrderByRegTimeDescAndIdAsc(Pageable pageable);
	
	AiPost findById(long id);
}
 