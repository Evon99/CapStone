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
@Table(name = "music_img")
@Getter
@Setter
public class MusicImg {

	@Id
	@Column(name = "music_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "musicImgName", nullable = false)
	private String imgName; // 음악 이미지명
	
	@Column(name = "oriMusicImgName", nullable = false)
	private String oriImgName; //음악 이미지 원본명
	
	@Column(name = "musicImgUrl", nullable = false)
	private String imgUrl; // 음악 이미지 주소
	
	@OneToOne
	@JoinColumn(name = "music_id")
	private Music music;
	
	public void updateMusicImg(String imgName, String oriImgName, String imgUrl) {
		this.imgName = imgName;
		this.oriImgName = oriImgName;
		this.imgUrl = imgUrl;
	}
}
