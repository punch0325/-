package com.icia.mboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageDTO {

	private int page;
	private int startPage;
	private int endPage;
	private int maxPage;
	private int startRow;
	private int endRow;
	
}
