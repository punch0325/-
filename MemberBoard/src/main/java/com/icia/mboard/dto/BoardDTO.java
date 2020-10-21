package com.icia.mboard.dto;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {

	private int bnumber;
	private String bwriter;
	private String btitle;
	private String bcontents;
	private Date bdate;
	private int bhits;
	private MultipartFile bfile;
	private String bfilename;
}
