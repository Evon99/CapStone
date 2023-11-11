package com.inhatc.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inhatc.web.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    public Optional<Member> findByLoginId(String name);
    public Optional<Member> findByEmail(String email);
    
    public Optional<Member> findByLoginId(String loginId);
    
    public Member findMemberById(Long Id);
    public Member findMemberByLoginId(String loginId);
//    Member findByUsername(String username);
 
    @Query("SELECT m.id FROM Member m WHERE m.loginId = :loginId")
    public Long findIdByLoginId(@Param("loginId") String loginId);
}