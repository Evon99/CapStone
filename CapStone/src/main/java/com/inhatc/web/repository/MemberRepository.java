package com.inhatc.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.web.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByName(String name);
}