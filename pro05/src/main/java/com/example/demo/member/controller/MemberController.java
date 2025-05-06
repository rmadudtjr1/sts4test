package com.example.demo.member.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.member.dto.MemberDTO;

import jakarta.servlet.http.HttpSession;

public interface MemberController {
	public String form(@PathVariable("formName") String formName);
	String membersList(Model model);
	String joinMember(MemberDTO dto, Model model);
	String detailMember(@RequestParam("id") String id, Model model);
	String updateMember(MemberDTO dto, Model model);
	String deleteMember(@RequestParam("id") String id, Model model);
	String login(@RequestParam("id") String id, 
			@RequestParam("pwd") String pwd, HttpSession session, Model model);
	String logout(HttpSession session, Model model);
}
