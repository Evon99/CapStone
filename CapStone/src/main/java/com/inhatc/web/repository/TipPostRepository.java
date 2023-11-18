package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inhatc.web.entity.RequestPost;
import com.inhatc.web.entity.TipPost;

public interface TipPostRepository extends JpaRepository<TipPost, Long>{

	@Query("SELECT tp FROM TipPost tp ORDER BY tp.regTime DESC, tp.id ASC")
    List<TipPost> findAllByOrderByRegTimeDescAndIdAsc(Pageable pageable);
	
	TipPost findById(long id);
}
 