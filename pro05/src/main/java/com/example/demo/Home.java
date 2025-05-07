package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {
	
	@GetMapping(value= {"/", "/home"})
	public String home() {
		return "home";
	}
	
}
