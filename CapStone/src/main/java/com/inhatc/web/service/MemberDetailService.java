package com.inhatc.web.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberDetailService {

	@Value("${memberImgLocation}")
	private String memberImgLocation;
	
	private final MemberDetailRepository memberDetailRepository;
	
	private final MemberRepository memberRepository;
	
	private final FileService fileService;
	
	private final HttpSession httpSession;
	
	public void savePictureFile(MultipartFile MultiMusicFile, String nickName) throws Exception{
        
		validateDuplicateNickName(nickName); // 닉네임 중복 검사
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		System.out.println("member:" + member);
		MemberDetail memberDetail = new MemberDetail();
		memberDetail.setMember(member);
		
		
		String oriPictureName = MultiMusicFile.getOriginalFilename();
        String pictureName = "";
        String pictureUrl = "";

        System.out.println(oriPictureName);
        //파일 업로드
        if(!StringUtils.isEmpty(oriPictureName)){
            pictureName = fileService.uploadFile(memberImgLocation, oriPictureName, MultiMusicFile.getBytes());
            pictureUrl = "/images/member/" + pictureName; // 파일 저장 장소
        }

       System.out.println(pictureUrl);
        //음악 이미지 정보 저장
        memberDetail.updateMemberDetail(nickName, oriPictureName, pictureName, pictureUrl);
        memberDetailRepository.save(memberDetail);
    }
	
public void updatePictureFile(MultipartFile MultiMusicFile, String nickName) throws Exception{
        
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		Member member = memberRepository.findByLoginId(user.getLoginId()).get();
		System.out.println("member:" + member);
		MemberDetail memberDetail = memberDetailRepository.findByMember_Id(member.getId());
		memberDetail.setMember(member);
		
		
		String oriPictureName = MultiMusicFile.getOriginalFilename();
        String pictureName = "";
        String pictureUrl = "";

        System.out.println(oriPictureName);
        //파일 업로드
        if(!StringUtils.isEmpty(oriPictureName)){
            pictureName = fileService.uploadFile(memberImgLocation, oriPictureName, MultiMusicFile.getBytes());
            pictureUrl = "/images/member/" + pictureName; // 파일 저장 장소
        }

       System.out.println(pictureUrl);
        //음악 이미지 정보 저장
        memberDetail.updateMemberDetail(nickName, oriPictureName, pictureName, pictureUrl);
        memberDetailRepository.save(memberDetail);
    }
	
	private void validateDuplicateNickName(String nickName) {
		MemberDetail findDetail = memberDetailRepository.findByNickname(nickName);
		if(findDetail != null) {
			System.out.println("닉네임 중복!");
			throw new IllegalStateException("중복된 닉네임입니다.");
		}
	}
}
