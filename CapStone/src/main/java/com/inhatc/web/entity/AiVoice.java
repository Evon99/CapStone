package com.inhatc.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ai_voice")
@Getter
@Setter
public class AiVoice {

	@Id
	@Column(name = "ai_voice_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String voiceName; // 보이스 목소리 파일명

	@Column(nullable = false)
	private String oriVoiceName; // 보이스 목소리 원본파일명
	
	@Column(nullable = false)
	private String voiceUrl; // 보이스 파일 주소
	
	@ManyToOne
	@JoinColumn(name = "ai_post_id")
	private AiPost aiPost;
	
}
