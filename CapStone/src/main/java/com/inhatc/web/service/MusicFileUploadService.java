package com.inhatc.web.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.inhatc.web.entity.MusicFile;
import com.inhatc.web.repository.MusicFileRepository;
import com.inhatc.web.repository.MusicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicFileUploadService {

	@Value("${musicFileLocation}")
    private String musicFileLocation;

    private final MusicFileRepository musicFileRepository;
    
    private final MusicRepository musicRepository;

    private final FileService musicFileService;

    private final HttpSession httpSession;
    
    public void saveMusicFile(MusicFile musicFile, MultipartFile MultiMusicFile) throws Exception{
        String oriMusicName = MultiMusicFile.getOriginalFilename();
        String musicName = "";
        String musicUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriMusicName)){
            musicName = musicFileService.uploadFile(musicFileLocation, oriMusicName, MultiMusicFile.getBytes());
            musicUrl = "/images/item/" + musicName; // 파일 저장 장소
        }

        //음악 이미지 정보 저장
        musicFile.updateMusicFile(oriMusicName, musicName, musicUrl);
        musicFileRepository.save(musicFile);
    }
}
