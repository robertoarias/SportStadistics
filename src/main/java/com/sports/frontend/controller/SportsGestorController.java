package com.sports.frontend.controller;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller("/")
public class SportsGestorController {

	private List<List<String>> paisesList=null;
	private List<List<String>> clubesList=null;
	
	@RequestMapping("/")
	public String index_(Model model, Authentication authentication)
	{			
		return "index";		
	}	
	

}
