package com.icia.mboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
		
	private int cnumber;
	private int cbnumber;
	private String cwriter;
	private String ccontents;
	
}
