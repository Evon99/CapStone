package com.inhatc.web.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.inhatc.web.entity.MemberDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDetailDto {

	private Long id;
	
	@NotBlank(message = "닉네임을 입력해주세요.")
	@Length(min=2, message = "2글자 이상 입력해주세요.")
	private String nickname;
	
	private String pictureName;
	
	private String oriPictureName;
	
	private String pictureUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static MemberDetailDto of(MemberDetail memberImg) {
		return modelMapper.map(memberImg, MemberDetailDto.class);
	}
}
