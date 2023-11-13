package com.inhatc.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.inhatc.web.entity.Music;
import com.inhatc.web.entity.RequestPost;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPostDto {

	private Long id;

	@NotBlank(message = "제목을 입력해주세요.")
	private String title; // 곡 제목

	@NotBlank(message = "AI 가수명을 입력해주세요.")
	private String content; // AI 가수명

	private static ModelMapper modelMapper = new ModelMapper();

	public RequestPost createRequestPost() {
		return modelMapper.map(this, RequestPost.class);
	}

	public static RequestPostDto of(RequestPost requestPost) {
		return modelMapper.map(requestPost, RequestPostDto.class);
	}

}
