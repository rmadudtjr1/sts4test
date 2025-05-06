package com.example.demo.member.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberControllerImpl implements MemberController{
	@Autowired
	MemberService memberService;
	
	@GetMapping("/{formName:.*Form}")
	public String form(String formName) {
		return "/member/"+formName;
	}

	@Override
	@PostMapping("/joinMember")
	public String joinMember(MemberDTO dto, Model model) {
		// TODO Auto-generated method stub
		MemberDTO member = memberService.save(dto);
		if (member != null) {
			model.addAttribute("message", dto.getId() + "회원 가입에 성공했습니다.");
		    model.addAttribute("redirectUrl", "/member/membersList");	
		} else {
			model.addAttribute("message", dto.getId() + "회원 가입에 실패했습니다. 다시 가입 하세요.");
		    model.addAttribute("redirectUrl", "/member/joinMemberForm");
		}
		return "/common/alert";
	}

	@Override
	@GetMapping("/detailMember")
	public String detailMember(String id, Model model) {
		// TODO Auto-generated method stub
		MemberDTO member = memberService.findById(id);
		model.addAttribute("member", member);
		return "/member/detailMember";
	}

	@Override
	@PostMapping("/updateMember")
	public String updateMember(MemberDTO dto, Model model) {
		// TODO Auto-generated method stub
		int result = memberService.updateMember(dto);
		
		if (result >= 1) {
			model.addAttribute("message", dto.getId() + "회원 수정에 성공했습니다.");
		    model.addAttribute("redirectUrl", "/member/membersList");	
		} else {
			model.addAttribute("message", dto.getId() + "회원 수정에 실패했습니다. 다시 수정 하세요.");
		    model.addAttribute("redirectUrl", "/member/detailMember?id="+dto.getId());
		}
		return "/common/alert";
	}

	@Override
	@GetMapping("/deleteMember")
	public String deleteMember(String id, Model model) {
		// TODO Auto-generated method stub
		int result = memberService.deleteMember(id);
		
		if (result >= 1) {
			model.addAttribute("message", id + "회원 정보를 삭제했습니다.");
		    model.addAttribute("redirectUrl", "/member/membersList");	
		} else {
			model.addAttribute("message", id + "회원 삭제에 실패했습니다. 다시 삭제 하세요.");
		    model.addAttribute("redirectUrl", "/member/detailMember?id="+ id);
		}
		return "/common/alert";
	}

	@Override
	@GetMapping("/membersList")
	public String membersList(Model model) {
		// TODO Auto-generated method stub
		List<MemberDTO> membersList = memberService.membersList();
		model.addAttribute("membersList", membersList);
		return "/member/membersList";
	}

	@Override
	@PostMapping("/login")
	public String login(String id, String pwd, HttpSession session, Model model) {
		// TODO Auto-generated method stub
		boolean result = memberService.login(id, pwd);
		
		
		if (result) {
			session.setAttribute("loginId", id);
			model.addAttribute("message", id + "님 환영합니다. 로그인에 성공했습니다.");
		    model.addAttribute("redirectUrl", "/");	
		} else {
			model.addAttribute("message", "아이디나 암호가 잘못 되었습니다. 다시 로그인 하세요.");
		    model.addAttribute("redirectUrl", "/member/loginForm");
		}
		return "/common/alert";
	}

	@Override
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		// TODO Auto-generated method stub
		session.invalidate();
		model.addAttribute("message", "로그아웃되었습니다.");
		model.addAttribute("redirectUrl", "/member/loginForm");
        return "/common/alert"; 
	}

	
}
