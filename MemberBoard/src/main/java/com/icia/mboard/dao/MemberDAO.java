package com.icia.mboard.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.mboard.dto.BoardDTO;
import com.icia.mboard.dto.MemberDTO;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSessionTemplate sql;
	
	public String idOverlap(String mid) {
		return sql.selectOne("Member.idOverlap",mid);
	}

	public int memberJoin(MemberDTO member) {
		if(member.getKakaoId() != null) {
			return sql.insert("Member.kakaoJoin", member);
		}else if(member.getNaverId() != null){
			return sql.insert("Member.naverJoin", member);
		}else {			
			return sql.insert("Member.memberJoin", member);        
		}
	}

	public String memberLogin(MemberDTO member) {
		return sql.selectOne("Member.memberLogin",member);
	}

	public List<MemberDTO> memberList() {
		return sql.selectList("Member.memberList");
	}

	public MemberDTO memberView(String mid) {
		return sql.selectOne("Member.memberView", mid);
	}

	public int memberUpdateProcess(MemberDTO member) {
		return sql.update("Member.memberUpdate",member);
	}

	public int memberDelete(String mid) {
		return sql.delete("Member.memberDelete",mid);
	}

	public String kakaoLogin(String kakaoId) {
		return sql.selectOne("Member.kakaoLogin",kakaoId);
	}

	public String naverLogin(String naverId) {
		return sql.selectOne("Member.naverLogin",naverId);
	}

	public List<BoardDTO> myList(String mid) {
		return sql.selectList("Board.myList", mid);
	}

	public int bCount(String mid) {
		return sql.selectOne("Board.bCount",mid);
	}

}
