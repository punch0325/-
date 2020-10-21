package com.icia.mboard.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.mboard.dto.BoardDTO;
import com.icia.mboard.dto.PageDTO;

@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSessionTemplate sql;
	
	public int boardWrite(BoardDTO board) {
		return sql.insert("Board.boardWrite" , board);
	}

	public List<BoardDTO> boardList() {
		return sql.selectList("Board.boardList");
	}

	public int listCount() {
		return sql.selectOne("Board.boardListCount");
	}

	public List<BoardDTO> boardListPaging(PageDTO paging) {
		return sql.selectList("Board.boardListPaging",paging);
	}

	public void boardHits(int bnumber) {
		sql.update("Board.boardHits",bnumber);
	}

	public BoardDTO boardView(int bnumber) {
		return sql.selectOne("Board.boardView",bnumber);
	}

	public int boardUpdate(BoardDTO board) {
		return sql.update("Board.boardUpdate",board);
	}

	public int boardDelete(int bnumber) {
		return sql.delete("Board.boardDelete",bnumber);
	}

	public List<BoardDTO> boardSearch(String search, String keyword) {
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("search", search);
		searchMap.put("keyword", keyword);
		return sql.selectList("Board.boardSearch",searchMap);
	}

	public List<BoardDTO> boardArray() {
		return sql.selectList("Board.boardArray");
	}

	public void mBoardDelete(String mid) {
		sql.delete("Board.mBoardDelete",mid);
	}


}
