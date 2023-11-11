package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inhatc.web.entity.MemberDetail;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long>{

	public MemberDetail findByNickname(String nickName);
	
	List<MemberDetail> findAllByNickname(String nickName);
	
	public MemberDetail findByMember_Id(Long Id);

	
	@Query("SELECT md.member.id FROM MemberDetail md WHERE md.nickname = :nickname")
    Long findMemberIdByNickname(@Param("nickname") String nickname);
}

