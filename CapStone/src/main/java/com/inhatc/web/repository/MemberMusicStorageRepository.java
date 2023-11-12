package com.inhatc.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inhatc.web.entity.MemberMusicStorage;

public interface MemberMusicStorageRepository extends JpaRepository<MemberMusicStorage, Long>{
	
	List<MemberMusicStorage> findByMember_Id(Long memberId);
	
	// member_id와 music_id를 기반으로 데이터를 찾아 삭제하는 메서드
    void deleteByMember_IdAndMusic_Id(Long memberId, Long musicId);
    
    @Query("SELECT m.music.id FROM MemberMusicStorage m WHERE m.member.id = :memberId")
    List<Long> findMusicIdsByMemberId(@Param("memberId") Long memberId);
}
