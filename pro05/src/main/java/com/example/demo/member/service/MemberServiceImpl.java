package com.example.demo.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<MemberDTO> membersList() {
		// TODO Auto-generated method stub
		return memberRepository.findAll();
	}

	@Override
	public MemberDTO save(MemberDTO dto) {
		// TODO Auto-generated method stub
		String encodedPwd = passwordEncoder.encode(dto.getPwd());
		dto.setPwd(encodedPwd);
		return memberRepository.save(dto);
	}

	@Override
	public MemberDTO findById(String id) {
		// TODO Auto-generated method stub
		MemberDTO member = memberRepository.findById(id).orElseThrow(() ->
    		new IllegalArgumentException("회원 정보가 없습니다."));
		return member;
	}

	@Override
	public int updateMember(MemberDTO dto) {
		// TODO Auto-generated method stub
		return memberRepository.updateMember(dto.getId(), dto.getPwd(), dto.getName(), dto.getEmail());
	}

	@Override
	public int deleteMember(String id) {
		// TODO Auto-generated method stub
		return memberRepository.deleteMember(id);
	}

	@Override
	public boolean login(String id, String pwd) {
		// TODO Auto-generated method stub
		MemberDTO member = memberRepository.findById(id).get();
		
		if (member != null) {
			String savePwd = member.getPwd();
	        if (passwordEncoder.matches(pwd, savePwd)) {	        	
	            return true;
	        }
		}
		return false;
	}

}
