package com.inhatc.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.inhatc.web.entity.MusicImg;
import com.inhatc.web.repository.MusicImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicImgService {

	@Value("${musicImgLocation}")
    private String musicImgLocation;

    private final MusicImgRepository musicImgRepository;

    private final FileService fileService;

    public void saveItemImg(MusicImg musicImg, MultipartFile musicImgFile) throws Exception{
        String oriImgName = musicImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(musicImgLocation, oriImgName, musicImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        //음악 이미지 정보 저장
        musicImg.updateMusicImg(oriImgName, imgName, imgUrl);
        musicImgRepository.save(musicImg);
    }
}
