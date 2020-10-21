package com.icia.mboard.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mboard.dto.BoardDTO;
import com.icia.mboard.service.BoardService;

@Controller
public class BoardController {
	
	private ModelAndView mav;
	
	@Autowired
	private BoardService bSer;
	
	@RequestMapping(value="/boardwriteform")
	public String boardWriteForm() {
		return "boardv/BoardWrite";
	}
	
	@RequestMapping(value="boardwrite")
	public ModelAndView boardWrite(@ModelAttribute BoardDTO board) 
			throws IllegalStateException, IOException {
		mav = bSer.boardWrite(board);
		return mav;
	}
	
	@RequestMapping(value="/boardlist")
	public ModelAndView boardList(@RequestParam(value="page",required=false,defaultValue="1") int page) {
		mav = bSer.boardListPaging(page);
		return mav;
	}
	
	@RequestMapping(value="/boardview")
	public ModelAndView boardView(@RequestParam("bnumber") int bnumber,@RequestParam(value="page",required=false,defaultValue="1") int page) {
		mav = bSer.boardView(bnumber,page);
		return mav;
	}
	
	@RequestMapping(value="/boardupdate")
	public ModelAndView boardUpdate(@RequestParam("bnumber") int bnumber) {
		mav = bSer.boardUpdate(bnumber);
		return mav;
	}
	
	@RequestMapping(value="/boardupdateprocess")
	public ModelAndView boardUpdateProcess(@ModelAttribute BoardDTO board) 
			throws IllegalStateException, IOException {
		mav = bSer.boardUpdateProcess(board);
		return mav;
	}
	
	@RequestMapping(value="/boarddelete")
	public ModelAndView boardDelete(@RequestParam("bnumber") int bnumber) {
		mav = bSer.boardDelete(bnumber);
		return mav;
	}
	
	@RequestMapping(value="/boardsearch")
	public ModelAndView boardSearch(@RequestParam("search") String search, @RequestParam("keyword") String keyword,@RequestParam(value="page",required=false,defaultValue="1") int page) {
		mav = bSer.boardSearch(search,keyword,page);
		return mav;
	}
	
	@RequestMapping(value="/boardarray")
	public @ResponseBody List<BoardDTO> boardArray(){
		List<BoardDTO> bList = bSer.boardArray();
		return bList;
		
	}
	
	
	/*
	 * @RequestMapping("/file/filedownload") public void
	 * fileDownload(HttpServletRequest request, HttpServletResponse response) {
	 * String saveDir =
	 * request.getSession().getServletContext().getRealPath("/resources/upload/file"
	 * ); String fileName = "20190223-223005277_939.jpg"; File file = new
	 * File(saveDir + "/" + fileName); FileInputStream fis = null;
	 * BufferedInputStream bis = null; ServletOutputStream sos = null; try { fis =
	 * new FileInputStream(file); bis = new BufferedInputStream(fis); sos =
	 * response.getOutputStream(); String reFilename = ""; boolean isMSIE =
	 * request.getHeader("user-agent").indexOf("MSIE") != -1 ||
	 * request.getHeader("user-agent").indexOf("Trident") != -1;
	 * 
	 * if(isMSIE) { reFilename = URLEncoder.encode("이미지 파일.jpg", "utf-8");
	 * reFilename = reFilename.replaceAll("\\+", "%20"); }else { reFilename = new
	 * String("이미지 파일.jpg".getBytes("utf-8"), "ISO-8859-1"); }
	 * 
	 * response.setContentType("application/octet-stream;charset=utf-8");
	 * response.addHeader("Content-Disposition",
	 * "attachment;filename=\""+reFilename+"\"");
	 * response.setContentLength((int)file.length()); int read = 0;
	 * 
	 * while((read = bis.read()) != -1) { sos.write(read); } }catch(IOException e) {
	 * e.printStackTrace(); }finally { try { sos.close(); bis.close(); }catch
	 * (IOException e) { e.printStackTrace(); } } }
	 */

	
}
