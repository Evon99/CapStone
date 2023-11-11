package com.inhatc.web.entity;

import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "music")
@Getter
@Setter
public class Music extends BaseTimeEntity{

	@Id
	@Column(name = "music_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String title; // 곡 제목
	
	@Column(nullable = false)
	private String aiSinger; // AI 가수명
	
	@Column(nullable = false)
	private String oriSinger; // 원래 가수명
	
	@Column(nullable = false)
	private String genre; // 장르
	
	@Column(nullable = false)
	private String musicName; // 음악 파일명
	
	@Column(nullable = false)
	private String oriMusicName; // 음악 파일 원본명
	
	@Column(nullable = false)
	private String musicUrl; // 음악 파일 주소
	
	@Column(name = "musicImgName", nullable = false)
	private String imgName; // 음악 이미지명
	
	@Column(name = "oriMusicImgName", nullable = false)
	private String oriImgName; //음악 이미지 원본명
	
	@Column(name = "musicImgUrl", nullable = false)
	private String imgUrl; // 음악 이미지 주소
	
	@Column(name = "\"like\"") // Escaping reserved keyword
	private int like; // 추천 수
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    private MemberDetail memberDetail;
	
	@Transient // DB에 매핑하지 않음
    private String uploaderNickname;
	
	@OneToMany(mappedBy = "music")
    private Set<LikedMusic> likedMusics;

	public void updateMusic(String title, String aiSinger, String oriSinger, String genre, String musicName, String oriMusicName,
			String musicUrl, String imgName, String oriImgName, String imgUrl) {
		this.title = title;
		this.aiSinger = aiSinger;
		this.oriSinger = oriSinger;
		this.genre = genre;
		this.musicName = musicName;
		this.oriMusicName = oriMusicName;
		this.musicUrl = musicUrl;
		this.imgName = imgName;
		this.oriImgName = oriImgName;
		this.imgUrl = imgUrl;
	}
	
	public void setUploaderNickname() {
        if (memberDetail != null) {
            this.uploaderNickname = memberDetail.getNickname();
        } else if (member != null) {
            this.uploaderNickname = member.getName();
        }
    }
	
}
