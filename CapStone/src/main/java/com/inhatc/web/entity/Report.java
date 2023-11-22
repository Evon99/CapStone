package com.inhatc.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "report")
@Getter
@Setter
public class Report extends BaseTimeEntity{

	@Id
	@Column(name = "report_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "reportContent")
	private String reportContent;
	
	@ManyToOne
	@JoinColumn(name = "music_id")
	private Music music;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    private MemberDetail memberDetail;
	
	@Transient // DB에 매핑하지 않음
    private String uploaderNickname;
	
	public void setUploaderNickname() {
        if (memberDetail != null) {
            this.uploaderNickname = memberDetail.getNickname();
        } else if (member != null) {
            this.uploaderNickname = member.getName();
        }
    }
}
