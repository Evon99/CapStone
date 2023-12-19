package com.inhatc.web.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.inhatc.web.constant.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Member extends BaseTimeEntity{
	
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본키
    
    @Column(nullable = false)
    private String name; //유저 이름(구별자)
    
    @Column(nullable = false)
    private String loginId; // 로그인 아이디
    
//    @Column(nullable = false)
//    private String nickname;
    
//    @Column(nullable = false)
//    private String password; //유저 비밀번호
    
    @Column(nullable = false)
    private String email; //유저 구글 이메일
    
    @Enumerated(EnumType.STRING)
    private Role role; //유저 권한 (일반 유저, 관리자)
    
    private String provider; //공급자 (google, facebook ...)
    
    private String providerId; //공급 아이디
    
//    private String pictureName; // 이미지 파일명
//     
//    private String oriPictureName; // 원본 이미지 파일명
    
    private String pictureUrl; // 유저 프로필 사진
    
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberDetail memberDetail;
    
    @OneToMany(mappedBy = "member")
    private Set<LikedMusic> likedMusics; 
    
    @OneToMany(mappedBy = "member")
    private Set<MemberMusicStorage> memberMusicStorage;
    
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<RequestComment> requestComment;
    
    @Builder
    public Member(String name, String loginId, String email, String provider, String providerId, Role role, String pictureUrl) {
        this.name = name;
//        this.nickname = nickname;
//        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.loginId = loginId;
//        this.pictureName = pictureName;
//        this.oriPictureName = oriPictureName;
        this.pictureUrl = pictureUrl;
    }
    
    public Member update(String name, String pictureUrl) {
    	this.name = name;
    	this.pictureUrl = pictureUrl;
    	
    	return this;
    }
    
    public String getRoleKey() {
        return this.role.getKey();
    }

//    public void updateMemberImg(String pictureName, String oriPictureName, String pictureUrl) {
//    	this.pictureName = pictureName;
//    	this.oriPictureName = oriPictureName;
//    	this.pictureUrl = pictureUrl;
//    }
	
    public MemberDetail getMemberDetail() {
        return memberDetail;
    }
    
}
