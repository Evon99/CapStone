package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inhatc.web.entity.RequestPost;

public interface RequestPostRepository extends JpaRepository<RequestPost, Long>{

	@Query("SELECT rp FROM RequestPost rp ORDER BY rp.regTime DESC, rp.id ASC")
    List<RequestPost> findAllByOrderByRegTimeDescAndIdAsc(Pageable pageable);
	
	RequestPost findById(long id);
}
 