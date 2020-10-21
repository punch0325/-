package com.icia.mboard.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mboard.dao.BoardDAO;
import com.icia.mboard.dao.CommentDAO;
import com.icia.mboard.dao.MemberDAO;
import com.icia.mboard.dto.BoardDTO;
import com.icia.mboard.dto.MemberDTO;

@Service
public class MemberService {

	private ModelAndView mav;
	
	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private BoardDAO bDAO;
	
	@Autowired
	private CommentDAO cDAO;
	
	@Autowired
	private HttpSession session;
	
	public ModelAndView memberJoin(MemberDTO member) 
			throws IllegalStateException, IOException {
		mav = new ModelAndView();
		
		MultipartFile mfile = member.getMfile();
		String mfilename = mfile.getOriginalFilename();
		String savePath = "D:\\source\\spring\\MemberBoard\\src\\main\\webapp\\resources\\img\\"
						  +mfilename;
		if(!mfile.isEmpty()) {
			mfile.transferTo(new File(savePath));
		}
		member.setMfilename(mfilename);
		
		int joinResult = mDAO.memberJoin(member);
		if (joinResult == 1) {
			mav.setViewName("memberv/MemberLogin");
		}else {
			mav.setViewName("memberv/MemberJoinFail");
		}
		return mav;
	}

	public String idOverlap(String mid) {
		String checkResult = mDAO.idOverlap(mid);
		String resultMag = null;
		if (checkResult == null) {
			resultMag="OK";
		}else {
			resultMag="NO";
		}
		return resultMag;
	}

	public ModelAndView memberLogin(MemberDTO member) {
		mav = new ModelAndView();
		
		String loginId = mDAO.memberLogin(member);
		if (loginId != null) {
			session.setAttribute("loginId", loginId);
			mav.setViewName("redirect:/boardlist");
		}else {
			mav.setViewName("memberv/MemberLoginFail");
		}
		return mav;
	}

	public ModelAndView memberList() {
		mav = new ModelAndView();
		List<MemberDTO> mList = mDAO.memberList();
		mav.addObject("mList" , mList);
		mav.setViewName("memberv/MemberList");
		return mav;
	}

	public MemberDTO memberView(String mid) {
		MemberDTO member = mDAO.memberView(mid);
		return member;
	}

	public ModelAndView memberUpdateProcess(MemberDTO member) throws IllegalStateException, IOException {
		mav = new ModelAndView();
		
		MultipartFile mfile = member.getMfile();
		String mfilename = mfile.getOriginalFilename();
		mfilename = System.currentTimeMillis() + "_" + mfilename; //파일 이름이 중복 되는 것을 방지하기 위해 파일 이름 앞에 시간을 불러오는 메소드를 추가해줌
		String savePath = "D:\\source\\spring\\MemberBoard\\src\\main\\webapp\\resources\\img\\"+mfilename;
		if(!mfile.isEmpty()) {
			mfile.transferTo(new File(savePath));
		}
		
		member.setMfilename(mfilename);
		int updateResult = mDAO.memberUpdateProcess(member);
		if(updateResult == 1) {
			mav.setViewName("redirect:/boardlist");	
		}else {
			mav.setViewName("boardv/BoardUpdateFail");
		}
		return mav;
	}

	public ModelAndView memberDelete(String mid) {
		cDAO.mCommentDelete(mid);
		bDAO.mBoardDelete(mid);
		int deleteResult = mDAO.memberDelete(mid);
		if(deleteResult == 1) {
			mav.setViewName("redirect:/memberlist");
		}else {
			mav.setViewName("memberv/MemberDeleteFail");
		}
		return mav;
	}

	public ModelAndView kakaoLogin(JsonNode profile) {
		mav = new ModelAndView();
		String kakaoId = profile.get("id").asText();
		String loginId = mDAO.kakaoLogin(kakaoId);
		
		session.setAttribute("loginId",loginId);
		mav.setViewName("redirect:/boardlist");
		return mav;
	}

	public ModelAndView naverLogin(String profile) throws ParseException {
		mav = new ModelAndView();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(profile);
		JSONObject naverUser = (JSONObject)obj;
		JSONObject userInfo = (JSONObject)naverUser.get("response");
		
		String naverId = (String)userInfo.get("id");
		String loginId = mDAO.naverLogin(naverId);
		session.setAttribute("loginId", loginId);
		mav.setViewName("redirect:/boardlist");
		
		return mav;
	}

	public ModelAndView myList() {
		mav = new ModelAndView();
		String mid = (String) session.getAttribute("loginId");
		List<BoardDTO> bList = mDAO.myList(mid);
		int bCount = mDAO.bCount(mid);
		mav.addObject("bList", bList);
		mav.addObject("bCount",bCount);
		mav.setViewName("memberv/MemberMyPage");
		return mav;
	}

}
