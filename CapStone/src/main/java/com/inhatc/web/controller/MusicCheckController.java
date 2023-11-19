package com.inhatc.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.datatype.Artwork;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class MusicCheckController {

	@PostMapping("/checkMusic")
	@ResponseBody
	public Map<String, Object> checkMusic(@RequestParam("file") MultipartFile file) throws IOException {
	    Map<String, Object> response = new HashMap<>();

	    // 파일이 오디오인지 여부를 확인하는 로직
	    AudioFileType isAudio = getAudioFileType(file.getBytes());
	    if (isAudio != AudioFileType.UNKNOWN) {
	        // 오디오 파일일 경우에는 Base64로 인코딩하여 클라이언트에 전달
	        System.out.println("음악 파일입니다. 형식: " + isAudio.name());
	        String encodedAudio = Base64.getEncoder().encodeToString(file.getBytes());
	        response.put("encodedAudio", encodedAudio);
	        
	        String originalFilename = file.getOriginalFilename();
	        response.put("musicTitle", originalFilename);
	    } else {
	        // 오디오 파일이 아닌 경우 기본 이미지 전달
	        System.out.println("오디오 파일이 아닙니다.");
	        // 기본 이미지 경로로 수정 (설정해야 하는 이미지 경로로 변경하세요)
	        response.put("defaultImage", "/static/images/profile_image.svg");
	    }
	    response.put("isAudio", isAudio);

	    return response;
	}
	
	@PostMapping("/checkVoice")
	@ResponseBody
	public Map<String, Object> checkVoice(@RequestParam("file") MultipartFile file) throws IOException {
	    Map<String, Object> response = new HashMap<>();

	    // 파일이 오디오인지 여부를 확인하는 로직
	    AudioFileType isAudio = getAudioFileType(file.getBytes());
	    if (isAudio != AudioFileType.UNKNOWN) {
	        // 오디오 파일일 경우에는 Base64로 인코딩하여 클라이언트에 전달
	        System.out.println("음악 파일입니다. 형식: " + isAudio.name());
	        String encodedAudio = Base64.getEncoder().encodeToString(file.getBytes());
	        response.put("encodedAudio", encodedAudio);
	        
	        String originalFilename = file.getOriginalFilename();
	        response.put("musicTitle", originalFilename);
	    } else {
	        // 오디오 파일이 아닌 경우 기본 이미지 전달
	        System.out.println("오디오 파일이 아닙니다.");
	        // 기본 이미지 경로로 수정 (설정해야 하는 이미지 경로로 변경하세요)
	        response.put("defaultImage", "/static/images/profile_image.svg");
	    }
	    response.put("isAudio", isAudio);

	    return response;
	}

	// 오디오 파일 형식을 나타내는 열거형
    public enum AudioFileType {
        MP3,
        WAV,
        FLAC,
        AIFF,
        ALAC,
        AAC,
        OGG,
        MP4,
        MP2,
        M4A,
        _3GP,
        _3G2,
        MJ2,
        AMR,
        WMA,
        UNKNOWN
    }
    
	// 주어진 파일이 어떤 오디오 형식인지 확인
    public static AudioFileType getAudioFileType(byte[] fileBytes) {
        if (isMp3(fileBytes)) {
            return AudioFileType.MP3;
        } else if (isWav(fileBytes)) {
            return AudioFileType.WAV;
        } else if (isFlac(fileBytes)) {
            return AudioFileType.FLAC;
        } else if (isAiff(fileBytes)) {
            return AudioFileType.AIFF;
        } else if (isAlac(fileBytes)) {
            return AudioFileType.ALAC;
        } else if (isAac(fileBytes)) {
            return AudioFileType.AAC;
        } else if (isOgg(fileBytes)) {
            return AudioFileType.OGG;
        } else if (isMp4(fileBytes)) {
            return AudioFileType.MP4;
        } else if (isMp2(fileBytes)) {
            return AudioFileType.MP2;
        } else if (isM4a(fileBytes)) {
            return AudioFileType.M4A;
        } else if (is3gp(fileBytes)) {
            return AudioFileType._3GP;
        } else if (is3g2(fileBytes)) {
            return AudioFileType._3G2;
        } else if (isMj2(fileBytes)) {
            return AudioFileType.MJ2;
        } else if (isAmr(fileBytes)) {
            return AudioFileType.AMR;
        } else if (isWma(fileBytes)) {
            return AudioFileType.WMA;
        } else {
            return AudioFileType.UNKNOWN;
        }
    }
	 // 각 파일 형식을 확인하는 메서드들
    private static boolean isMp3(byte[] fileBytes) {
        // MP3 파일의 매직 넘버 확인
        return fileBytes.length >= 3 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 0, 3), new byte[]{(byte) 0xFF, (byte) 0xFB, (byte) 0xE0});
    }

    private static boolean isWav(byte[] fileBytes) {
        // WAV 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 0, 4), new byte[]{'R', 'I', 'F', 'F'});
    }

    private static boolean isFlac(byte[] fileBytes) {
        // FLAC 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 0, 4), new byte[]{'f', 'L', 'a', 'C'});
    }

    private static boolean isAiff(byte[] fileBytes) {
        // AIFF 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 0, 4), new byte[]{'F', 'O', 'R', 'M'});
    }

    private static boolean isAlac(byte[] fileBytes) {
        // ALAC 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 4, 8), new byte[]{'f', 'r', 'e', 'e'});
    }

    private static boolean isAac(byte[] fileBytes) {
        // AAC 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 0, 4), new byte[]{'\0', '\0', '\0', '\0'});
    }

    private static boolean isOgg(byte[] fileBytes) {
        // Ogg/Vorbis 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 0, 4), new byte[]{'O', 'g', 'g', 'S'});
    }

    private static boolean isMp4(byte[] fileBytes) {
        // MP4 파일의 매직 넘버 확인
        return fileBytes.length >= 12 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 4, 12), new byte[]{'f', 't', 'y', 'p', 'm', 'p', '4', '2'});
    }

    private static boolean isMp2(byte[] fileBytes) {
        // MP2 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 0, 4), new byte[]{'I', 'D', '3', '\3'});
    }

    private static boolean isM4a(byte[] fileBytes) {
        // M4A 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 4, 8), new byte[]{'f', 't', 'y', 'p'});
    }

    private static boolean is3gp(byte[] fileBytes) {
        // 3GP 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 4, 8), new byte[]{'3', 'g', 'p', '5'});
    }

    private static boolean is3g2(byte[] fileBytes) {
        // 3G2 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 4, 8), new byte[]{'3', 'g', 'p', '6'});
    }

    private static boolean isMj2(byte[] fileBytes) {
        // MJ2 파일의 매직 넘버 확인
        return fileBytes.length >= 4 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 4, 8), new byte[]{'m', 'j', 'p', '2'});
    }

    private static boolean isAmr(byte[] fileBytes) {
        // AMR 파일의 매직 넘버 확인
        return fileBytes.length >= 6 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 0, 6), new byte[]{'#', '!', 'A', 'M', 'R', '\n'});
    }

    private static boolean isWma(byte[] fileBytes) {
        // WMA 파일의 매직 넘버 확인
        return fileBytes.length >= 16 &&
                Arrays.equals(Arrays.copyOfRange(fileBytes, 0, 16),
                        new byte[]{'0', 'x', '3', 'f', '0', 'x', 'W', 'M', 'A', '9', '0', 'x', '0', 'x', '0', 'x'});
    }
    
   

 

}
