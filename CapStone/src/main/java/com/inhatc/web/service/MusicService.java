package com.inhatc.web.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.util.StringUtils;

import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.LikedMusic;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.MemberMusicStorage;
import com.inhatc.web.entity.Music;
import com.inhatc.web.repository.LikedMusicRepository;
import com.inhatc.web.repository.MemberMusicStorageRepository;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.repository.MusicRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicService {

	@Value("${musicImgLocation}")
	private String musicImgLocation;
	
	@Value("${musicFileLocation}")
	private String musicFileLocation;
	
	private final MusicRepository musicRepository;
	
	private final MemberRepository memberRepository;
	
	private final FileService fileService;
	
	private final HttpSession httpSession;
	
	private final LikedMusicRepository likedMusicRepository;
	
	private final MemberMusicStorageRepository memberMusicStorageRepository;
	
	public void saveMusic(MultipartFile musicImgFile, MultipartFile musicFile, String title, String aiSinger, String oriSinger, String genre) throws Exception {
		
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		
		Music music = new Music();
		music.setMember(member);
		
		
		String oriMusicName = musicFile.getOriginalFilename(); 
		String musicName = ""; 
		String musicUrl = ""; 
		
		String oriImgName = musicImgFile.getOriginalFilename(); 
		String imgName = ""; 
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriMusicName)){
            musicName = fileService.uploadFile(musicFileLocation, oriMusicName, musicFile.getBytes());
            musicUrl = "/images/music/file/" + musicName; // 파일 저장 장소
        }
		
		if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(musicImgLocation, oriImgName, musicImgFile.getBytes());
            imgUrl = "/images/music/img/" + imgName; // 파일 저장 장소
        }
		
		music.updateMusic(title, aiSinger, oriSinger, genre, musicName, oriMusicName, musicUrl, imgName, oriImgName, imgUrl);
		musicRepository.save(music);		
		
	}
	
	public List<Music> getRecentMusics(int count) {
	    PageRequest pageRequest = PageRequest.of(0, count);
	    List<Music> recentMusics = musicRepository.findAllByOrderByRegTimeAsc(pageRequest);

	    for (Music music : recentMusics) {
	        music.setUploaderNickname();
	    }

	    return recentMusics;
	}
	
	public List<Music> getTopLikeMusics(int count) {
	    PageRequest pageRequest = PageRequest.of(0, count);
	    List<Music> topLikeMusics = musicRepository.findAllByOrderByLikeDesc(pageRequest);

	    for (Music music : topLikeMusics) {
	        music.setUploaderNickname();
	    }

	    return topLikeMusics;
	}
	
	public int updateLikeCount(Long musicId) {
		// 이미 좋아요를 눌렀는지 확인
	    if (httpSession.getAttribute("liked_music_" + musicId) != null) {
	        // 이미 좋아요를 눌렀음을 클라이언트에 알림
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다.");
	    }

	    // 좋아요 수 업데이트 로직 수행
	    Music music = musicRepository.findById(musicId)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND, "Music not found with id " + musicId));

	    // 좋아요 수를 1 증가시킴
	    music.setLike(music.getLike() + 1);

	    // 업데이트된 좋아요 수를 반환
	    int updatedLikeCount = musicRepository.save(music).getLike();

	    // 세션에 좋아요를 눌렀음을 표시
	    httpSession.setAttribute("liked_music_" + musicId, true);

	    return updatedLikeCount;
	}

	boolean existsByMusicAndMemberId(Music music, Long memberId) {
        return likedMusicRepository.existsByMusic_IdAndMember_Id(music.getId(), memberId);
    }

    public void saveLikedMusic(Member member, Long musicId) {
        Music music = getMusicById(musicId);

        // 기존의 좋아요 여부 체크 로직을 호출하여 수정
        if (existsByMusicAndMemberId(music, member.getId())) {
            // 이미 좋아요를 눌렀으므로 중복 처리
            System.out.println("이미 좋아요를 눌렀습니다.");
            return;
        }

        // 좋아요를 누른 적이 없다면 저장
        LikedMusic likedMusic = new LikedMusic();
        likedMusic.setMember(member);
        likedMusic.setMusic(music);
        likedMusicRepository.save(likedMusic);
    }
    
    public Music getMusicById(Long musicId) {
        return musicRepository.findById(musicId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Music not found with id " + musicId));
    }
    
    public List<Music> searchMusicByKeyword(String keyword) {
        // Keyword에 해당하는 title, aiSinger, oriSinger, genre가 포함된 음악을 조회
        return musicRepository.findByTitleContainingOrAiSingerContainingOrOriSingerContainingOrGenreContaining(keyword, keyword, keyword, keyword);
    }
    
    public List<Music> searchMusicByFilter(String filter, String keyword) {
    	if(filter.equals("title")) {
    		return musicRepository.findByTitle(keyword);
    	} else if(filter.equals("aiSinger")) {
    		return musicRepository.findByAiSinger(keyword);
    	} else if(filter.equals("oriSinger")) {
    		return musicRepository.findByOriSinger(keyword);
    	} else if(filter.equals("genre")) {
    		return musicRepository.findByGenre(keyword);
    	}
		return null;
    	
    }
    
    // 사용자가 이미 음악을 추가했는지 검사
    public boolean hasMemberAddMusic(Member member, Long musicId) {
    	return member.getMemberMusicStorage().stream().anyMatch(memberMusicStorage -> memberMusicStorage.getMusic().getId().equals(musicId));
    }
    
    // 음악 보관함 추가
    public void updateAddMusic(Member member, Long musicId) {
    	Music music = getMusicById(musicId);
    	MemberMusicStorage memberMusicStorage = new MemberMusicStorage();
    	memberMusicStorage.setMember(member);
    	memberMusicStorage.setMusic(music);
    	memberMusicStorageRepository.save(memberMusicStorage);
    }
    
    // 음악 보관함 삭제
    public void deleteAddMusic(Member member, Long musicId) {
    	memberMusicStorageRepository.deleteByMember_IdAndMusic_Id(member.getId(), musicId);
    }
    
}
