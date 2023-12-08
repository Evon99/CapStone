package com.inhatc.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.inhatc.web.config.auth.LoginUser;
import com.inhatc.web.dto.RequestPostDto;
import com.inhatc.web.dto.SessionUser;
import com.inhatc.web.entity.AiComment;
import com.inhatc.web.entity.AiPost;
import com.inhatc.web.entity.Member;
import com.inhatc.web.entity.MemberDetail;
import com.inhatc.web.entity.RequestComment;
import com.inhatc.web.entity.RequestPost;
import com.inhatc.web.entity.TipComment;
import com.inhatc.web.entity.TipPost;
import com.inhatc.web.repository.MemberDetailRepository;
import com.inhatc.web.repository.MemberRepository;
import com.inhatc.web.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommunityController {

	private final MemberRepository memberRepository;
	
	private final MemberDetailRepository memberDetailRepository;
	
	private final PostService postService;
	
	@GetMapping("/requestnoticeboard")
	public String requestNoticeBoard(@RequestParam(value="page", defaultValue="0") int page, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		Page<RequestPost> paging = postService.requestPostPaging(page, "", "");
		
		model.addAttribute("paging", paging);
		
		System.out.println("요청곡 페이지로 이동");
		return "requestnoticeboard";
	}
	
	@PostMapping("/requestnoticeboard")
	public String requestSearchNoticeBoard(@RequestParam(value="page", defaultValue="0") int page, @RequestParam("keyword") String keyword, @RequestParam("filter") String filter, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		Page<RequestPost> paging = postService.requestPostPaging(page, filter, keyword);
		
		model.addAttribute("paging", paging);
		
		return "requestnoticeboard";
	}
	
	
	@GetMapping("/tipnoticeboard")
	public String tipNoticeBoard(@RequestParam(value="page", defaultValue="0") int page, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		Page<TipPost> paging = postService.tipPostPaging(page, "", "");
		
		model.addAttribute("paging", paging);
		
		return "tipnoticeboard";
	}
	
	@PostMapping("/tipnoticeboard")
	public String tipSearchNoticeBoard(@RequestParam(value="page", defaultValue="0") int page, @RequestParam("keyword") String keyword, @RequestParam("filter") String filter, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		Page<TipPost> paging = postService.tipPostPaging(page, filter, keyword);
		
		model.addAttribute("paging", paging);
		
		return "tipnoticeboard";
	}
	
	@GetMapping("/voicenoticeboard")
	public String voiceNoticeBoard(@RequestParam(value="page", defaultValue="0") int page, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		Page<AiPost> paging = postService.aiPostPaging(page, "", "");
		
		model.addAttribute("paging", paging);
		
		return "voicenoticeboard";
	}
	
	@PostMapping("/voicenoticeboard")
	public String voiceSearchNoticeBoard(@RequestParam(value="page", defaultValue="0") int page, @RequestParam("keyword") String keyword, @RequestParam("filter") String filter, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		Page<AiPost> paging = postService.aiPostPaging(page, filter, keyword);
		
		model.addAttribute("paging", paging);
		
		return "voicenoticeboard";
	}
	
	@GetMapping("/private/requestpostwrite")
	public String requestPostWrite(Model model, @LoginUser SessionUser user, HttpSession session) {
		
		return "requestpostwrite";
	}
	
	@GetMapping("/private/tippostwrite")
	public String tipPostWrite(Model model, @LoginUser SessionUser user, HttpSession session) {
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
	            model.addAttribute("requestPostDto", new RequestPostDto());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		return "tippostwrite";
	}
	
	@GetMapping("/private/voicepostwrite")
	public String voicePostWrite(Model model, @LoginUser SessionUser user, HttpSession session) {
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
	            model.addAttribute("requestPostDto", new RequestPostDto());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	        	
	        }
	    }
		return "voicepostwrite";
	}
	
	@PostMapping("/private/requestpostwrite")
	public String requestPostWrite(@RequestParam("title") String title, @RequestParam("content") String content, @LoginUser SessionUser user, Model model) {
		
		System.out.println("게시글 내용:" + content);
		if(title.isBlank()) {
			System.out.println("titleEmpty");
			return "titleEmpty";
		}
		
		if(content.isBlank()) {
			System.out.println("contentEmpty");
			return "contentEmpty";
		}
		
		try {
			  postService.saveRequestPost(user, title, content);
      } catch (Exception e) {
          model.addAttribute("errorMessage", e.getMessage());
          return "requestpostwrite";
      }
		
//		Page<RequestPost> requestPost = postService.getRequestList(0, "");
//		model.addAttribute("post", requestPost);
		
		Page<RequestPost> paging = postService.requestPostPaging(0, "", "");
		model.addAttribute("paging", paging);
		
		return "requestnoticeboard";
	}
	
	@PostMapping("/private/tippostwrite")
	public String tipPostWrite(@RequestParam("title") String title, @RequestParam("content") String content, @LoginUser SessionUser user, Model model) {
		
		if(title.isBlank()) {
			System.out.println("titleEmpty");
			return "titleEmpty";
		}
		
		if(content.isBlank()) {
			System.out.println("contentEmpty");
			return "contentEmpty";
		}
		
		try {
			  postService.saveTipPost(user, title, content);
      } catch (Exception e) {
          model.addAttribute("errorMessage", e.getMessage());
          return "tippostwrite";
      }
		
//		List<TipPost> tipPost = postService.getTipList(0);
//		model.addAttribute("post", tipPost);
		
		Page<TipPost> paging = postService.tipPostPaging(0, "", "");
		model.addAttribute("paging", paging);
		
		return "tipnoticeboard";
	}
	
	@PostMapping("/private/voicepostwrite")
	public String voicePostWrite(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("voiceImgFile")MultipartFile voiceImgFile,
			@RequestParam("voiceFileUpload")MultipartFile voiceFileUpload, @LoginUser SessionUser user, Model model) {
		
		System.out.println("title:" + title);
		if(title.isBlank()) {
			System.out.println("titleEmpty");
			return "titleEmpty";
		}
		
		if(content.isBlank()) {
			System.out.println("contentEmpty");
			return "contentEmpty";
		}
		
		try {
			  postService.saveVoice(user, voiceImgFile, voiceFileUpload, title, content);
      } catch (Exception e) {
          model.addAttribute("errorMessage", e.getMessage());
          return "voicepostwrite";
      }
		
//		List<AiPost> aiPost = postService.getVoiceList(0);
//		model.addAttribute("post", aiPost);
		
		Page<AiPost> paging = postService.aiPostPaging(0, "", "");
		model.addAttribute("paging", paging);
		
		return "voicenoticeboard";
	}
	
	
	@GetMapping("/requestpost/{postId}")
	public String requestPost(@RequestParam(value="page", defaultValue="0") int page, @PathVariable long postId, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		RequestPost requestPost = postService.getRequestPost(postId);
		
		model.addAttribute("requestPost", requestPost);
		
//		List<RequestComment> requestCommentList = postService.getRequestComment(postId, page);
		
		Page<RequestComment> requestCommentList = postService.getRequestComment(postId, page);
		
		model.addAttribute("comment", requestCommentList);
		
		postService.updateRequestView(postId); // 게시글 조회수 증가
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		return "requestpost";
	}
	
	@GetMapping("/tippost/{postId}")
	public String tipPost(@RequestParam(value="page", defaultValue="0") int page, @PathVariable long postId, Model model, @LoginUser SessionUser user, HttpSession session) {
		
		TipPost tipPost = postService.getTipPost(postId);
		
		model.addAttribute("tipPost", tipPost);
		
//		List<TipComment> tipCommentList = postService.getTipComment(postId, page);
		
		Page<TipComment> tipCommentList = postService.getTipComment(postId, page);
		
		model.addAttribute("comment", tipCommentList);
		
		postService.updateTipView(postId); // 게시글 조회수 증가
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		return "tippost";
	}
	
	@GetMapping("/voicepost/{postId}")
	public String voicePost(@RequestParam(value="page", defaultValue="0") int page, @PathVariable long postId, Model model, @LoginUser SessionUser user, HttpSession session) throws IOException {
		
		AiPost aiPost = postService.getVoicePost(postId);
		
		model.addAttribute("aiPost", aiPost);
		
//		List<TipComment> tipCommentList = postService.getTipComment(postId, 0);
		
		Page<AiComment> VoiceCommentList = postService.getVoiceComment(postId, page);
		
		model.addAttribute("comment", VoiceCommentList);
		
		postService.updateAiPostView(postId); // 게시글 조회수 증가
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		return "voicepost";
	}
	
	@PostMapping("/private/requestcommentwrite")
	public String requestCommentWrite(@RequestParam("postId") long postId, @RequestParam("comment") String comment, @LoginUser SessionUser user, Model model, HttpSession session) {
		
		if(comment.isBlank()) {
			System.out.println("commentEmpty");
			return "commentEmpty";
		}
		
		try {
			  postService.saveRequestComment(user, comment, postId);
	      } catch (Exception e) {
	          model.addAttribute("errorMessage", e.getMessage());
	          return "requestpost";
	      }
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		
		RequestPost requestPost = postService.getRequestPost(postId);
		
		model.addAttribute("requestPost", requestPost);
		
//		List<RequestComment> requestCommentList = postService.getRequestComment(postId, 0);
		
		Page<RequestComment> requestCommentList = postService.getRequestComment(postId, 0);
		
		model.addAttribute("comment", requestCommentList);
		
		return "requestpost";
	}
	
	@PostMapping("/private/tipcommentwrite")
	public String tipCommentWrite(@RequestParam("postId") long postId, @RequestParam("comment") String comment, @LoginUser SessionUser user, Model model, HttpSession session) {
		
		if(comment.isBlank()) {
			System.out.println("commentEmpty");
			return "commentEmpty";
		}
		
		try {
			  postService.saveTipComment(user, comment, postId);
	      } catch (Exception e) {
	          model.addAttribute("errorMessage", e.getMessage());
	          return "tippost";
	      }
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		
		TipPost tipPost = postService.getTipPost(postId);
		
		model.addAttribute("tipPost", tipPost);
		
//		List<TipComment> tipCommentList = postService.getTipComment(postId, 0);
		
		Page<TipComment> tipCommentList = postService.getTipComment(postId, 0);
		
		model.addAttribute("comment", tipCommentList);
		
		return "tippost";
	}
	
	@PostMapping("/private/voicecommentwrite")
	public String voiceCommentWrite(@RequestParam("postId") long postId, @RequestParam("comment") String comment, @LoginUser SessionUser user, Model model, HttpSession session) throws IOException {
		
		if(comment.isBlank()) {
			System.out.println("commentEmpty");
			return "commentEmpty";
		}
		
		System.out.println("목소리 게시글 ID: " + postId);
		try {
			  postService.saveAiPostComment(user, comment, postId);
	      } catch (Exception e) {
	    	  System.out.println("댓글 저장 실패");
	          model.addAttribute("errorMessage", e.getMessage());
	          e.printStackTrace();
	          return "voicepost";
	      }
		
		if (user != null) {
	        Optional<Member> optionalMember = memberRepository.findByLoginId(user.getLoginId());

	        if (optionalMember.isPresent()) {
	        	// 로그인 유저 정보
	            Member loginMember = optionalMember.get();
	            MemberDetail loginMemberDetail = memberDetailRepository.findByMember_Id(loginMember.getId());
	            
	            
	            // Check if memberDetail is null
	            if (loginMemberDetail == null || loginMemberDetail.getNickname() == null) {
	                // Clear the session
	                session.invalidate();
	                // Redirect to the home page
	                return "redirect:/";
	            }

	            model.addAttribute("session", user);
	            model.addAttribute("loginNickname", loginMemberDetail.getNickname());
	            model.addAttribute("loginMemberId", loginMember.getId());
//	            System.out.println("멤버디테일:" + memberDetail.getPictureUrl());

	         // 로그인유저 프로필 이미지 가져오기
	            if (loginMemberDetail.getPictureUrl().isEmpty()) {
	                model.addAttribute("loginPictureUrl", loginMember.getPictureUrl());
//	                System.out.println("url: " + member.getPictureUrl());
	            } else {
	                model.addAttribute("loginPictureUrl", loginMemberDetail.getPictureUrl());
	            }
	            
	        } else {
	            // Optional이 비어 있다면 처리할 로직 추가
	        }
	    }
		
		System.out.println("aiPost 찾기전 ");
		AiPost aiPost = postService.getVoicePost(postId);
		System.out.println("타이틀: "+ aiPost.getTitle());
		model.addAttribute("aiPost", aiPost);
		
//		List<TipComment> tipCommentList = postService.getTipComment(postId, 0);
		
		Page<AiComment> VoiceCommentList = postService.getVoiceComment(postId, 0);
		
		model.addAttribute("comment", VoiceCommentList);
		
		return "voicepost";
	}
	
	
}
