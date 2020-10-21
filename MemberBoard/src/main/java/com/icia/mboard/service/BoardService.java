package com.icia.mboard.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mboard.dto.PageDTO;
import com.icia.mboard.dao.BoardDAO;
import com.icia.mboard.dao.CommentDAO;
import com.icia.mboard.dto.BoardDTO;
import com.icia.mboard.dto.CommentDTO;

@Service
public class BoardService {

	private ModelAndView mav;
	
	private static final int PAGE_LIMIT = 3;
	private static final int BLOCK_LIMIT = 3;
	
	@Autowired
	private BoardDAO bDAO;
	
	@Autowired
	private CommentDAO cDAO;
	
	public ModelAndView boardList() {
		mav = new ModelAndView();
		List<BoardDTO> bList = bDAO.boardList();
		mav.addObject("bList",bList);
		mav.setViewName("boardv/BoardList");
		return mav;
	}

	public ModelAndView boardWrite(BoardDTO board) 
			throws IllegalStateException, IOException {
		mav = new ModelAndView();
		
		MultipartFile bfile = board.getBfile();
		String bfilename = bfile.getOriginalFilename();
		bfilename = System.currentTimeMillis()+"_"+bfilename;
		String savePath ="D:\\source\\spring\\MemberBoard\\src\\main"
						 + "\\webapp\\resources\\img\\"+bfilename;
		
		if(!bfile.isEmpty()) {
			bfile.transferTo(new File(savePath));
		}
		board.setBfilename(bfilename);
		
		int writeResult = bDAO.boardWrite(board);
		if(writeResult == 1) {
			mav.setViewName("redirect:/boardlist");
		}else {
			mav.setViewName("boardv/BoardWriteFail");
		}
		return mav;

	}

	public ModelAndView boardListPaging(int page) {
		mav = new ModelAndView();
		int listCount = bDAO.listCount();
		int startRow =(page-1)*PAGE_LIMIT+1;
		int endRow = page*PAGE_LIMIT;
		
		PageDTO paging = new PageDTO();
		paging.setStartRow(startRow);
		paging.setEndRow(endRow);
		List<BoardDTO> bList = bDAO.boardListPaging(paging);
		
		int maxPage = (int)(Math.ceil((double)listCount/PAGE_LIMIT));
		int startPage = (((int)(Math.ceil((double)page/BLOCK_LIMIT)))-1)*BLOCK_LIMIT+1;
		
		int endPage = startPage+BLOCK_LIMIT-1;
		if(endPage>maxPage) {
			endPage=maxPage;
		}
		
		paging.setPage(page);
		paging.setStartPage(startPage);
		paging.setEndPage(endPage);
		paging.setMaxPage(maxPage);
		
		mav.addObject("paging",paging);
		mav.addObject("bList",bList);
		mav.setViewName("boardv/BoardList");
		return mav;
	}

	public ModelAndView boardView(int bnumber, int page) {
		mav = new ModelAndView();
		
		bDAO.boardHits(bnumber);
		BoardDTO board = bDAO.boardView(bnumber);
		List<CommentDTO> cList = cDAO.commentList(bnumber);
		
		mav.addObject("board",board);
		mav.addObject("page",page);
		mav.addObject("cList",cList);
		mav.setViewName("boardv/BoardView");
		return mav;
	}

	public ModelAndView boardUpdate(int bnumber) {
		mav = new ModelAndView();
		BoardDTO board = bDAO.boardView(bnumber);
		mav.addObject("board", board);
		mav.setViewName("boardv/BoardUpdate");
		return mav;
	}

	public ModelAndView boardUpdateProcess(BoardDTO board) 
			throws IllegalStateException, IOException {
		mav = new ModelAndView();
		MultipartFile bfile = board.getBfile();
		String bfilename = bfile.getOriginalFilename();
		String savePath ="D:\\source\\spring\\MemberBoard\\src\\"
						+ "main\\webapp\\resources\\img\\"+bfilename;
		
		if(!bfile.isEmpty()) {
			bfile.transferTo(new File(savePath));
		}
		board.setBfilename(bfilename);
		
		int updateResult = bDAO.boardUpdate(board);
		if(updateResult == 1) {
			mav.setViewName("redirect:/boardlist");
		}else {
			mav.setViewName("boardv/BoardUpdateFail");
		}
		return mav;
	}

	public ModelAndView boardDelete(int bnumber) {
		mav = new ModelAndView();
		cDAO.commentDelete(bnumber);
		int deleteResult = bDAO.boardDelete(bnumber);
		if(deleteResult == 1) {
			mav.setViewName("redirect:/boardlist");
		}else {
			mav.setViewName("boardv/BoardDeleteFail");
		}
		return mav;
	}

	public ModelAndView boardSearch(String search, String keyword, int page) {
		mav = new ModelAndView();
		int listCount = bDAO.listCount();
		int startRow =(page-1)*PAGE_LIMIT+1;
		int endRow = page*PAGE_LIMIT;
		
		PageDTO paging = new PageDTO();
		paging.setStartRow(startRow);
		paging.setEndRow(endRow);
		
		int maxPage = (int)(Math.ceil((double)listCount/PAGE_LIMIT));
		int startPage = (((int)(Math.ceil((double)page/BLOCK_LIMIT)))-1)*BLOCK_LIMIT+1;
		
		int endPage = startPage+BLOCK_LIMIT-1;
		if(endPage>maxPage) {
			endPage=maxPage;
		}
		
		paging.setPage(page);
		paging.setStartPage(startPage);
		paging.setEndPage(endPage);
		paging.setMaxPage(maxPage);
		
		List<BoardDTO> sList = bDAO.boardSearch(search,keyword);
		mav.addObject("paging",paging);
		mav.addObject("bList",sList);
		mav.setViewName("boardv/BoardList");
		return mav;
	}

	public List<BoardDTO> boardArray() {
		List<BoardDTO> bList = bDAO.boardArray();
		return bList;
	}

}
