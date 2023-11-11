package com.inhatc.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "music_file")
@Getter
@Setter
public class MusicFile {

	@Id
	@Column(name = "music_file_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String musicName; // 음악 파일명
	
	@Column(nullable = false)
	private String oriMusicName; // 음악 파일 원본명
	
	@Column(nullable = false)
	private String musicUrl; // 음악 파일 주소
	
	@OneToOne
	@JoinColumn(name = "music_id")
	private Music music;
	
	public void updateMusicFile(String musicName, String oriMusicName, String musicUrl) {
		this.musicName = musicName;
		this.oriMusicName = oriMusicName;
		this.musicUrl = musicUrl;
	}
}
