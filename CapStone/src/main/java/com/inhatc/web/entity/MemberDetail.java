package com.inhatc.web.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member_detail")
@Getter
@Setter
public class MemberDetail implements Serializable{
	
	@Id
	@Column(name = "member_detail_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, name="memberNickName")
	private String nickname; // 유저 닉네임
	
	@Column(nullable = false)
	private String pictureName; // 이미지 파일명
	
	@Column(nullable = false)
	private String oriPictureName; // 원본 이미지 파일명
	
	@Column(nullable = false)
	private String pictureUrl; // 유저 프로필 사진
	
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Transient
	private Long memberId;
	
	@Transient
	private int followerCount;
	
	
	public void updateMemberDetail(String nickName, String oriPictureName, String pictureName, String pictureUrl) {
    	this.nickname = nickName;
    	this.oriPictureName = oriPictureName;
		this.pictureName = pictureName;
    	this.pictureUrl = pictureUrl;
    }
	
	public void updateMemberPicture(String oriPictureName, String pictureName, String pictureUrl) {
    	this.oriPictureName = oriPictureName;
		this.pictureName = pictureName;
    	this.pictureUrl = pictureUrl;
    }
	
	public Long getMemberId() {
		return member.getId();
	}
}
