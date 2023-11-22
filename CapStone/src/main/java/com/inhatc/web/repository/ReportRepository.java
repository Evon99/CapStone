package com.inhatc.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inhatc.web.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{

	@Query("SELECT rp FROM Report rp ORDER BY rp.regTime DESC")
    Page<Report> findAllByOrderByRegTimeDesc(Pageable pageable);
	
	@Query("SELECT rp FROM Report rp WHERE rp.music.title LIKE %:title% OR rp.music.aiSinger LIKE %:title% OR rp.music.oriSinger LIKE %:title% ORDER BY rp.regTime DESC")
	Page<Report> findAllByTitleContainingOrderByRegTimeDesc(@Param("title") String title, Pageable pageable);

	@Query("SELECT rp FROM Report rp " +
		       "JOIN rp.memberDetail md " +
		       "WHERE md.nickname = :nickname " +
		       "ORDER BY rp.regTime DESC")
    Page<Report> findAllByNicknameOrderByRegTimeDesc(@Param("nickname") String nickname, Pageable pageable);

}
 