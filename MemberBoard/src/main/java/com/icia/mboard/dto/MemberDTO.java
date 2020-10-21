package com.icia.mboard.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
	private String mid;
	private String kakaoId;
	private String naverId;
	private String mpw;
	private String mname;
	private String mbirth;
	private String memail;
	private String maddress;
	private String mphone;
	private String mfilename;
	private MultipartFile mfile;
}
