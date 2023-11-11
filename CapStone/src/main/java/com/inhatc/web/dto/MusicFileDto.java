package com.inhatc.web.dto;

import org.modelmapper.ModelMapper;

import com.inhatc.web.entity.MusicFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicFileDto {

	private Long id;
	
	private String musicName; // 음악 파일명
	
	private String oriMusicName; // 음악 파일 원본명
	
	private String musicUrl; // 음악 파일 주소
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static MusicFileDto of(MusicFile musicFile) {
		return modelMapper.map(musicFile, MusicFileDto.class);
	}
}
