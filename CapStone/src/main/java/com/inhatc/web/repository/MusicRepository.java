package com.inhatc.web.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inhatc.web.entity.Music;

public interface MusicRepository extends JpaRepository<Music, Long>{
	List<Music> findAllByOrderByRegTimeDesc(Pageable pageable);
	
	List<Music> findAllByOrderByLikeDesc(Pageable pageable);
	
	List<Music> findByTitleContainingOrAiSingerContainingOrOriSingerContainingOrGenreContaining(String title, String aiSinger, String oriSinger, String genre);
	
	@Query("SELECT m FROM Music m WHERE " +
	           "(:keyword IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	List<Music> findByTitle(@Param("keyword") String keyword);

	@Query("SELECT m FROM Music m WHERE " +
	           "(:keyword IS NULL OR LOWER(m.aiSinger) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	List<Music> findByAiSinger(@Param("keyword") String keyword);

	@Query("SELECT m FROM Music m WHERE " +
	           "(:keyword IS NULL OR LOWER(m.oriSinger) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	List<Music> findByOriSinger(@Param("keyword") String keyword);

	@Query("SELECT m FROM Music m WHERE " +
	           "(:keyword IS NULL OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	List<Music> findByGenre(@Param("keyword") String keyword);
	
	List<Music> findByMemberIdOrderByRegTimeDesc(Long memberId);
	
	List<Music> findByIdIn(List<Long> addMusicId);
	
	List<Music> findByGenreOrderByLikeDesc(String genre);
	
	Music findById(long id);
	
	@Transactional
    @Modifying
    @Query("DELETE FROM Music m WHERE m.id = :musicId")
    void deleteByMusicId(@Param("musicId") Long musicId);
}
