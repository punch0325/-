package com.icia.mboard.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icia.mboard.dto.CommentDTO;
import com.icia.mboard.service.CommentService;

@Controller
@RequestMapping("/comment/*")
public class CommentController {
	@Autowired
	private CommentService cSer;
	
	@RequestMapping(value="/commentwrite")
	public @ResponseBody List<CommentDTO> commentWrite(@ModelAttribute CommentDTO comment){
		List<CommentDTO> cList = cSer.commentWrite(comment);
		return cList;
	}
}
