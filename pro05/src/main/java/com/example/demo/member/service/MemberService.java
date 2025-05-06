package com.example.demo.member.service;

import java.util.List;
import com.example.demo.member.dto.MemberDTO;

public interface MemberService {
	List<MemberDTO> membersList();
	MemberDTO save(MemberDTO dto);
	MemberDTO findById(String id);
	int updateMember(MemberDTO dto);
	int deleteMember(String id);
	boolean login(String id, String pwd);
}
