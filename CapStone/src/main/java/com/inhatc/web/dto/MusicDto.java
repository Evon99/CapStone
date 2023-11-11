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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicDto {

	private Long id;

	@NotBlank(message = "곡 제목을 입력해주세요.")
	private String title; // 곡 제목

	@NotBlank(message = "AI 가수명을 입력해주세요.")
	private String aiSinger; // AI 가수명

	@NotBlank(message = "본래 가수명을 입력해주세요.")
	private String oriSinger; // 원래 가수명

	private String genre; // 장르

	private int like; // 추천 수

	private MusicFileDto musicFileDto = new MusicFileDto();

	private List<Long> musicFileIds = new ArrayList<>();

	private MusicImgDto musicImgDto = new MusicImgDto();

	private List<Long> musicImgIds = new ArrayList<>();

	private static ModelMapper modelMapper = new ModelMapper();

	public Music createMusic() {
		return modelMapper.map(this, Music.class);
	}

	public static MusicDto of(Music music) {
		return modelMapper.map(music, MusicDto.class);
	}

}
