package com.icia.mboard.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.icia.mboard.api.KakaoJoinApi;
import com.icia.mboard.api.KakaoLoginApi;
import com.icia.mboard.dto.MemberDTO;
import com.icia.mboard.service.MemberService;
import com.icia.mboard.api.NaverJoinApi;
import com.icia.mboard.api.NaverLoginApi;

@Controller
public class MemberController {

	private ModelAndView mav;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private MemberService mSer;
	
	@Autowired
	private KakaoJoinApi kJoin;
	
	@Autowired
	private KakaoLoginApi kLogin;
	
	@Autowired
	private NaverJoinApi nJoin;
	
	@Autowired
	private NaverLoginApi nLogin;
	
	@RequestMapping(value="/")
	public String home() {
		return "home";
	}
	
	@RequestMapping(value="/gomain")
	public String main() {
		return "Main";
	}
	
	@RequestMapping(value="/memberjoinform")
	public String memberJoinForm() {
		return "memberv/MemberJoin";
	}
	
	@RequestMapping(value="/memberloginform")
	public String memberLoginForm() {
		return "memberv/MemberLogin";
	}
	
	@RequestMapping(value="/memberlogout")
	public String memberLogout() {
		session.invalidate();
		return "memberv/MemberLogin";
	}
	
	@RequestMapping(value="/membermypage")
	public ModelAndView myPage() {
		mav = mSer.myList();
		return mav;
	}
	
	@RequestMapping(value="/memberjoin")
	public ModelAndView memberJoin(@ModelAttribute MemberDTO member) 
			throws IllegalStateException, IOException {
		System.out.println("controller"+member.toString());
		mav = mSer.memberJoin(member);
		return mav;
	}
	
	@RequestMapping(value="/idoverlap")
	public @ResponseBody String idOverlap(@RequestParam("mid") String mid) {
		String resultMag = mSer.idOverlap(mid);
		return resultMag;
	}
	
	@RequestMapping(value="/memberlogin")
	public ModelAndView memberLogin(@ModelAttribute MemberDTO member) {
		mav = mSer.memberLogin(member);
		return mav;
	}
	
	@RequestMapping(value="/memberlist")
	public ModelAndView memberList() {
		mav = mSer.memberList();
		return mav;
	}
	
	@RequestMapping(value="/memberview")
	public @ResponseBody MemberDTO memberView(@RequestParam("mid") String mid) {
		MemberDTO member = mSer.memberView(mid);
		return member;
	}
	
	@RequestMapping(value="/memberupdate")
	public @ResponseBody MemberDTO memberUpdate(@RequestParam("mid") String mid) {
		MemberDTO member = mSer.memberView(mid);
		return member;
	}
	
	@RequestMapping(value="/memberupdateprocess")
	public ModelAndView memberUpdateProcess(@ModelAttribute MemberDTO member) 
		   throws IllegalStateException, IOException {
		mav = mSer.memberUpdateProcess(member);
		return mav;
	}
	
	@RequestMapping(value="memberdelete")
	public ModelAndView memberDelete(@RequestParam("mid") String mid) {
		mav = mSer.memberDelete(mid);
		return mav;
	}
	
	@RequestMapping(value="kakaojoin")
	public ModelAndView kakaoJoin(HttpSession session) {
		mav = new ModelAndView();
		
		String kakaoUrl = kJoin.getAuthorizationUrl(session);
		mav.addObject("kakaoUrl",kakaoUrl);
		mav.setViewName("memberv/MemberKakao");
		return mav;
	}
	
	@RequestMapping(value="/kakaojoinok")
	public ModelAndView kakaoJoinOK(@RequestParam("code") String code, HttpSession session) {
		JsonNode token = kJoin.getAccessToken(code);
		JsonNode profile = kJoin.getKakaoUserInfo(token.path("access_token"));

		String kakaoId = profile.get("id").asText();

		mav = new ModelAndView();
		mav.addObject("kakaoId", kakaoId);
		mav.setViewName("memberv/MemberJoin");
		return mav;
	}
	
	@RequestMapping(value="/kakaologin")
	public ModelAndView kakaoLogin(HttpSession session) {
		String kakaoUrl = kLogin.getAuthorizationUrl(session);
		mav = new ModelAndView();
		mav.addObject("kakaoUrl", kakaoUrl);
		mav.setViewName("memberv/MemberKakao");
		return mav;
	}
	
	@RequestMapping(value="/kakaologinok")
	public ModelAndView kakaoLoginOK(@RequestParam("code") String code, HttpSession session) {
		JsonNode token = kLogin.getAccessToken(code);
		JsonNode profile = kLogin.getKakaoUserInfo(token.path("access_token"));
		
		mav = mSer.kakaoLogin(profile);
		return mav;
	}
	
	@RequestMapping(value="/naverjoin")
	public ModelAndView naverJoin(HttpSession session) {
		String naverUrl = nJoin.getAuthorizationUrl(session);
		mav = new ModelAndView();
		mav.addObject("naverUrl", naverUrl);
		mav.setViewName("memberv/MemberNaver");
		return mav;
	}
	
	@RequestMapping(value="/naverjoinok")
	public ModelAndView naverJoinOK(@RequestParam("code") String code, @RequestParam("state") String state,HttpSession session) throws IOException, ParseException {
		mav = new ModelAndView();
		OAuth2AccessToken oauthToken = nJoin.getAccessToken(session, code, state);
		String profile = nJoin.getUserProfile(oauthToken);
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(profile);
		
		JSONObject naverUser = (JSONObject)obj;
		JSONObject userInfo = (JSONObject)naverUser.get("response");
		String naverId = (String) userInfo.get("id");
		
		mav.addObject("naverId", naverId);
		mav.setViewName("memberv/MemberJoin");
		return mav;
	}
	
	@RequestMapping(value="/naverlogin")
	public ModelAndView naverLogin(HttpSession session) {
		String naverUrl = nLogin.getAuthorizationUrl(session);
		mav = new ModelAndView();
		mav.addObject("naverUrl", naverUrl);
		mav.setViewName("memberv/MemberNaver");
		return mav;
	}
	
	@RequestMapping(value="/naverloginok")
	public ModelAndView naverLoginOK(@RequestParam("code") String code, @RequestParam("state") String state,HttpSession session) throws IOException, ParseException {
		OAuth2AccessToken oauthToken = nLogin.getAccessToken(session, code, state);
		String profile = nLogin.getUserProfile(oauthToken);
		mav = mSer.naverLogin(profile);
		
		return mav;
	}
	
}
