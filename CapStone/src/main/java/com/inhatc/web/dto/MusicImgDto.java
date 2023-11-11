package com.inhatc.web.dto;

import org.modelmapper.ModelMapper;

import com.inhatc.web.entity.MusicImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicImgDto {

	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static MusicImgDto of(MusicImg musicImg) {
		return modelMapper.map(musicImg, MusicImgDto.class);
	}
}
