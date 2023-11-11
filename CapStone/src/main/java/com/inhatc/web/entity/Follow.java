package com.inhatc.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member_follow")
@Getter
@Setter
public class Follow {

	@Id
	@Column(name = "member_follow_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_member_id")
	private Member fromMember;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_member_id")
	private Member toMember;

	@Transient
    private Long fromMemberId;
	
	@Transient // DB에 매핑하지 않음
    private String fromMemberNickname;
	
	@Transient // DB에 매핑하지 않음
    private String fromMemberImgUrl;
	
	@Transient
    private Long toMemberId;
	
	@Transient // DB에 매핑하지 않음
    private String toMemberNickname;
	
	@Transient // DB에 매핑하지 않음
    private String toMemberImgUrl;
	
	public Long getFromMemberId() {
        return fromMember.getId();
    }
	
	public String getFromMemberNickname() {
        return fromMember.getMemberDetail().getNickname();
    }
	
	public String getFromMemberImgUrl() {
		if(fromMember.getMemberDetail().getPictureUrl().isEmpty()) {
			return fromMember.getPictureUrl();
		}
        return fromMember.getMemberDetail().getPictureUrl();
    }
	
	public Long getToMemberId() {
        return toMember.getId();
    }
	
	public String getToMemberNickname() {
        return toMember.getMemberDetail().getNickname();
    }
	
	public String getToMemberImgUrl() {
		if(toMember.getMemberDetail().getPictureUrl().isEmpty()) {
			return toMember.getPictureUrl();
		}
        return toMember.getMemberDetail().getPictureUrl();
    }
}
