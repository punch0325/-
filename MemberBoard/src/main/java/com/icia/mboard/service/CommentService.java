package com.icia.mboard.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.mboard.dao.CommentDAO;
import com.icia.mboard.dto.CommentDTO;

@Service
public class CommentService {

	@Autowired
	private CommentDAO cDAO;
	
	public List<CommentDTO> commentWrite(CommentDTO comment) {
		int writeResult = cDAO.commentWrite(comment);
		List<CommentDTO> cList = new ArrayList<CommentDTO>();
		if(writeResult == 1) {
			int cbnumber = comment.getCbnumber();
			cList = cDAO.commentList(cbnumber);
		}else {
			cList = null;
		}
		return cList;
	}

}
