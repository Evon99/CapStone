package com.inhatc.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inhatc.web.entity.MusicFile;

public interface MusicFileRepository extends JpaRepository<MusicFile, Long> {

}
