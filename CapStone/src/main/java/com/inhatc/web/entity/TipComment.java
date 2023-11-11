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
@Table(name = "tip_comment")
@Getter
@Setter
public class TipComment {

	@Id
	@Column(name = "tip_comment_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tip_post_id")
	private TipPost tipPost;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
}
