package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.inhatc.web.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long>{

	@Query("SELECT COUNT(f) FROM Follow f WHERE f.fromMember.id = :fromMemberId AND f.toMember.id = :toMemberId")
    int countByFromMemberIdAndToMemberId(@Param("fromMemberId") Long fromMemberId, @Param("toMemberId") Long toMemberId);
	
	@Modifying
	@Transactional
	void deleteByFromMember_IdAndToMember_Id(Long fromMemberId, Long toMemberId);
	
	List<Follow> findByToMember_Id(Long toMemberId);
	
	List<Follow> findByFromMember_Id(Long fromMemberId);
	
	// 팔로워 리스트
	@Query("SELECT f FROM Follow f JOIN FETCH f.fromMember fm JOIN FETCH f.toMember tm WHERE tm.id = :toMemberId")
    List<Follow> findFollowerListWithMemberDetails(@Param("toMemberId") Long toMemberId);
	
	// 팔로잉 리스트
	@Query("SELECT f FROM Follow f JOIN FETCH f.fromMember fm JOIN FETCH f.toMember tm WHERE fm.id = :fromMemberId")
    List<Follow> findFollowingListWithMemberDetails(@Param("fromMemberId") Long fromMemberId);
}
